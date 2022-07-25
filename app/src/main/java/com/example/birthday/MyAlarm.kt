package com.example.birthday

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.widget.Switch
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
        val doofEntriesIndices = 0..25
        val doofEntriesTitle = "Placeholder Notif Title"
        val doofMessages =  listOf(
            "Placeholder!",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Hiiiiiiiiiiiiiiiiiiiiiiiiiiii",
            "Hihihihihihihihihihihihi",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "Placeholder",
            "ababababababababa"
        )
    }
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        if (prevIntentAction != intent.action) {
            // either starting or switching frequency
            if (intent.action.toString() == "second") {
                secToMinThresh =  notifNum + 30
                minToHourThresh = -1
            }
            else if (intent.action.toString() == "minute") {
                minToHourThresh = notifNum + 30
                secToMinThresh = -1
            } else {
                secToMinThresh = -1
                minToHourThresh = -1
            }
            prevIntentAction = intent.action.toString()
        }
        val messageNum = doofEntriesIndices.random()
        var builder = NotificationCompat.Builder(context, "Recurring Notif")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(doofEntriesTitle)
            .setContentText(doofMessages.elementAt(messageNum))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notifNum, builder.build())
        }

        incNotifNum()

        if (secToMinThresh != -1) {
            if (notifNum >= secToMinThresh) {
                minToHourThresh = notifNum + 30
                secToMinThresh = -1
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent.setAction("minute"),
                    PendingIntent.FLAG_NO_CREATE)
                alarmManager.setRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(),
                    60*1000,
                    pendingIntent
                )
            } else {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                alarmManager.setExact(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 1000,
                    pendingIntent
                )
            }
        } else if (minToHourThresh != -1 && notifNum >= minToHourThresh) {
            minToHourThresh = -1
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            var pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_NO_CREATE)
            if (pendingIntent != null && alarmManager != null) {
                Toast.makeText(context, "Canceling", Toast.LENGTH_SHORT).show()
                alarmManager.cancel(pendingIntent)
            }
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent.setAction("hour"),
                PendingIntent.FLAG_NO_CREATE)
            alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                3600*1000,
                pendingIntent
            )
        }
    }
}
