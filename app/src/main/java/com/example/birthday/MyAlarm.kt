package com.example.birthday

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService

class MyAlarm : BroadcastReceiver() {
    companion object {
        var notifNum = 0
        fun incNotifNum(){notifNum += 1}
        fun resetNotifNum(){notifNum = 0}
        var secToMinThresh = -1
        var minToHourThresh = -1
        var prevIntentAction = ""
    }
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        Toast.makeText(context, "Alarm! " + notifNum + intent.action, Toast.LENGTH_SHORT).show()

        var builder = NotificationCompat.Builder(context, "Recurring Notif")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Hi!" + notifNum)
            .setContentText("ababababababababa")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notifNum, builder.build())
        }

        incNotifNum()

        if (notifNum >= 2) {
            Toast.makeText(context, "if statement", Toast.LENGTH_SHORT).show()
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                    PendingIntent.FLAG_NO_CREATE)
            if (pendingIntent != null && alarmManager != null) {
                Toast.makeText(context, "Canceling", Toast.LENGTH_SHORT).show()
                alarmManager.cancel(pendingIntent)
            }
        }
    }
}
