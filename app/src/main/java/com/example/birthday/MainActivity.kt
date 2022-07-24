package com.example.birthday

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Switch
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun createNotificationChannel() {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Recurring Notif"
                val descriptionText = "Placeholder"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("Recurring Notif", name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

        createNotificationChannel()

        val secSwitch: Switch = findViewById(R.id.sec)
        val minSwitch: Switch = findViewById(R.id.min)
        val hourSwitch: Switch = findViewById(R.id.hour)

        secSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this, MyAlarm::class.java).setAction("second")
                val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
                alarmManager.setRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 20 * 1000,
                    20*1000,
                    pendingIntent
                )
                Toast.makeText(applicationContext, "sec al set", Toast.LENGTH_SHORT).show()

                if (minSwitch.isChecked) {
                    minSwitch.toggle()
                }
                if (hourSwitch.isChecked) {
                    hourSwitch.toggle()
                }
            } else {
                Toast.makeText(applicationContext, "sec uncheck", Toast.LENGTH_SHORT).show()
            }
        }

        minSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this, MyAlarm::class.java).setAction("minute")
                val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
                alarmManager.setRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 20 * 1000,
                    20*1000,
                    pendingIntent
                )
                Toast.makeText(applicationContext, "min al set", Toast.LENGTH_SHORT).show()

                if (secSwitch.isChecked) {
                    secSwitch.toggle()
                }
                if (hourSwitch.isChecked) {
                    hourSwitch.toggle()
                }
            } else {

            }
        }

        hourSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this, MyAlarm::class.java).setAction("hour")
                val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
                alarmManager.setRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 20 * 1000,
                    20*1000,
                    pendingIntent
                )
                Toast.makeText(applicationContext, "hr al set", Toast.LENGTH_SHORT).show()

                if (minSwitch.isChecked) {
                    minSwitch.toggle()
                }
                if (secSwitch.isChecked) {
                    secSwitch.toggle()
                }
            } else {

            }
        }
    }
}