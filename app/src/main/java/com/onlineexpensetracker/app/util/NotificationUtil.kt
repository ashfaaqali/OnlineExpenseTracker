package com.onlineexpensetracker.app.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.onlineexpensetracker.app.MainActivity
import com.onlineexpensetracker.app.R

object NotificationUtil {

    fun showDebitNotification(context: Context?, amount: Int, date: String, time: String) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(AppConstants.FRAGMENT_TO_OPEN, AppConstants.ADD_EXPENDITURE_FRAGMENT)
            putExtra(AppConstants.AMOUNT, amount)
            putExtra(AppConstants.DATE, date)
            putExtra(AppConstants.TIME, time)
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Create a notification channel for devices running Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "debit_notification_channel",
                "Debit Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create a notification
        val notification = NotificationCompat.Builder(context!!, "debit_notification_channel")
            .setSmallIcon(R.drawable.ic_clock)
            .setContentTitle("Debit Notification")
            .setContentText("You have received a debit message")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()

        // Show the notification
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(1, notification)
            }
        }
    }
}
