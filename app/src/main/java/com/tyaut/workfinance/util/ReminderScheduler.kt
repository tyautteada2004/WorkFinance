package com.tyaut.workfinance.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.tyaut.workfinance.data.db.entity.ShiftEntity
import com.tyaut.workfinance.data.repository.ShiftRepository
import com.tyaut.workfinance.data.repository.WorkplaceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val shiftRepo: ShiftRepository,
    private val workplaceRepo: WorkplaceRepository,
) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    /**
     * シフト開始の minutesBefore 分前に通知をスケジュール。
     * 0 以下なら通知なし。過去の時刻は無視。
     */
    fun scheduleShiftReminder(shift: ShiftEntity, minutesBefore: Int) {
        if (minutesBefore <= 0) return
        val triggerAt = shift.scheduledStart - minutesBefore * 60_000L
        if (triggerAt <= System.currentTimeMillis()) return

        val pi = buildShiftPendingIntent(shift, minutesBefore) ?: return
        setExactAlarm(triggerAt, pi)
    }

    /** スケジュール済みのシフトリマインドをキャンセル */
    fun cancelShiftReminder(shiftId: Long) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = ReminderReceiver.ACTION_SHIFT_REMINDER
        }
        val pi = PendingIntent.getBroadcast(
            context,
            shiftId.toInt(),
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pi?.let { alarmManager.cancel(it) }
    }

    /**
     * 端末再起動後の全シフトリマインド再登録。
     * BroadcastReceiver の goAsync() 内から呼ぶ。
     */
    suspend fun rescheduleAll() {
        val workplaceMap = workplaceRepo.observeAll().first().associateBy { it.id }
        for (shift in shiftRepo.getUnconfirmed()) {
            val minutesBefore = workplaceMap[shift.workplaceId]?.preWorkNotifyMinutes ?: continue
            scheduleShiftReminder(shift, minutesBefore)
        }
    }

    // -------------------------------------------------------------------------

    private fun buildShiftPendingIntent(shift: ShiftEntity, minutesBefore: Int): PendingIntent? {
        val shiftDate = shift.scheduledStart.millisToLocalDate()
        val timeFmt = java.time.format.DateTimeFormatter.ofPattern("HH:mm")
        val startTime = shift.scheduledStart.toLocalDateTime().format(timeFmt)

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = ReminderReceiver.ACTION_SHIFT_REMINDER
            putExtra(ReminderReceiver.EXTRA_ID, shift.id.toInt())
            putExtra(ReminderReceiver.EXTRA_TITLE, "出勤リマインド: ${shift.workplaceName}")
            putExtra(ReminderReceiver.EXTRA_BODY, "${minutesBefore}分後に出勤（${startTime}〜）")
        }
        return PendingIntent.getBroadcast(
            context,
            shift.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun setExactAlarm(triggerAt: Long, pi: PendingIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            // 正確なアラーム権限がない場合は近似アラームにフォールバック
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAt, pi)
            return
        }
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pi)
    }
}
