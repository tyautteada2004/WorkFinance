package com.tyaut.workfinance.util

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.tyaut.workfinance.R
import com.tyaut.workfinance.WorkFinanceApplication
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {

    @Inject lateinit var scheduler: ReminderScheduler

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> rescheduleAll()
            ACTION_SHIFT_REMINDER       -> notifyShiftReminder(context, intent)
            ACTION_PAYMENT_REMINDER     -> notifyPaymentReminder(context, intent)
        }
    }

    /**
     * 端末再起動後: AlarmManager のアラームはリセットされるため全件再登録する。
     * goAsync() でブロードキャストの処理時間を延長しコルーチンを実行。
     */
    private fun rescheduleAll() {
        val pending = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                scheduler.rescheduleAll()
            } finally {
                pending.finish()
            }
        }
    }

    private fun notifyShiftReminder(context: Context, intent: Intent) {
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "出勤リマインド"
        val body  = intent.getStringExtra(EXTRA_BODY)  ?: ""
        notify(context, title, body, WorkFinanceApplication.CHANNEL_SHIFT_REMINDER, intent.getIntExtra(EXTRA_ID, 0))
    }

    private fun notifyPaymentReminder(context: Context, intent: Intent) {
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "引落・給料日リマインド"
        val body  = intent.getStringExtra(EXTRA_BODY)  ?: ""
        notify(context, title, body, WorkFinanceApplication.CHANNEL_PAYMENT_REMINDER, intent.getIntExtra(EXTRA_ID, 0))
    }

    private fun notify(context: Context, title: String, body: String, channel: String, id: Int) {
        val nm = context.getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .build()
        nm.notify(id, notification)
    }

    companion object {
        const val ACTION_SHIFT_REMINDER   = "com.tyaut.workfinance.SHIFT_REMINDER"
        const val ACTION_PAYMENT_REMINDER = "com.tyaut.workfinance.PAYMENT_REMINDER"
        const val EXTRA_TITLE = "title"
        const val EXTRA_BODY  = "body"
        const val EXTRA_ID    = "notification_id"
    }
}
