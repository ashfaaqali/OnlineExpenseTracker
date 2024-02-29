package com.ali.onlinepaymenttracker.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ali.onlinepaymenttracker.R

object NotificationUtil {

    fun showDebitNotification(context: Context?) {
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
