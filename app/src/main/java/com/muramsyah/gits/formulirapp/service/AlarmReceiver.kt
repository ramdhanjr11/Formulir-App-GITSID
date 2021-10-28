package com.muramsyah.gits.formulirapp.service

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import com.muramsyah.gits.formulirapp.R
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val ID_REPEATING = 101
        private const val TITLE = "Reminder"
        private const val MESSAGE = "Ayo masuk aplikasi sekarang!"
        private const val CHANNEL_ID = "1"
        private const val CHANNEL_NAME = "reminder"
        private const val NOTIF_ID = 1
        const val IS_NOTIF = "is_notif"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val isNotif = intent.getBooleanExtra(IS_NOTIF, false)

        if (isNotif) {
            showAlarmNotification(context)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun setRepeatingAlarm(context: Context) {
        val bundle = Bundle()
        bundle.putBoolean(IS_NOTIF, true)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(IS_NOTIF, true)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun isAlarmSet(context: Context): Boolean {
        val intent = Intent(context, AlarmReceiver::class.java)

        return PendingIntent.getBroadcast(context, ID_REPEATING, intent, PendingIntent.FLAG_NO_CREATE) != null
    }

    private fun showAlarmNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(TITLE)
            .setContentText(MESSAGE)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(2000, 2000, 2000, 2000, 2000, 2000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(2000, 2000, 2000, 2000, 2000, 2000)

            builder.setChannelId(CHANNEL_ID)

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIF_ID, builder.build())
    }
}