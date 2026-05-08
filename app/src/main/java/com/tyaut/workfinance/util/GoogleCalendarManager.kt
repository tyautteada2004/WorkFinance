package com.tyaut.workfinance.util

import android.Manifest
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.provider.CalendarContract
import androidx.core.content.ContextCompat
import com.tyaut.workfinance.data.db.entity.ShiftEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleCalendarManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val contentResolver = context.contentResolver

    fun hasPermission(): Boolean =
        ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) ==
            PackageManager.PERMISSION_GRANTED

    /**
     * シフトのカレンダーイベントを作成し、イベントIDを返す。
     * 権限なし・エラー時は null を返し、シフト保存には影響しない。
     */
    suspend fun addShiftEvent(shift: ShiftEntity): String? = withContext(Dispatchers.IO) {
        if (!hasPermission()) return@withContext null
        val calendarId = findPrimaryCalendarId() ?: return@withContext null

        val values = ContentValues().apply {
            put(CalendarContract.Events.CALENDAR_ID, calendarId)
            put(CalendarContract.Events.TITLE, "出勤: ${shift.workplaceName}")
            put(CalendarContract.Events.DTSTART, shift.scheduledStart)
            put(CalendarContract.Events.DTEND, shift.scheduledEnd)
            put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
            shift.scheduledWage?.let {
                put(CalendarContract.Events.DESCRIPTION, "予定賃金: ¥$it")
            }
        }
        runCatching {
            contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)?.lastPathSegment
        }.getOrNull()
    }

    /**
     * 既存のカレンダーイベントを削除する。
     * 権限なし・イベント未存在時は何もしない。
     */
    suspend fun deleteEvent(eventId: String): Unit = withContext(Dispatchers.IO) {
        if (!hasPermission()) return@withContext
        val id = eventId.toLongOrNull() ?: return@withContext
        val uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id)
        runCatching { contentResolver.delete(uri, null, null) }
    }

    // -------------------------------------------------------------------------

    /**
     * デバイスのプライマリカレンダーIDを取得する。
     * IS_PRIMARY=1 を優先し、見つからなければ Google アカウントの先頭カレンダーを使う。
     */
    private fun findPrimaryCalendarId(): Long? {
        val projection = arrayOf(CalendarContract.Calendars._ID)
        val visibleFilter = "${CalendarContract.Calendars.VISIBLE} = 1"

        // プライマリカレンダーを検索
        contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            projection,
            "${CalendarContract.Calendars.IS_PRIMARY} = 1 AND $visibleFilter",
            null, null
        )?.use { cursor ->
            if (cursor.moveToFirst()) return cursor.getLong(0)
        }

        // フォールバック: Google アカウントに属するカレンダー
        contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            projection,
            "${CalendarContract.Calendars.ACCOUNT_TYPE} = 'com.google' AND $visibleFilter",
            null, null
        )?.use { cursor ->
            if (cursor.moveToFirst()) return cursor.getLong(0)
        }

        return null
    }
}
