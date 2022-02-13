package com.seif.notificatiion

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "1"
    private val NOTIFICATION_ID = 0
    private val CHANNEL_NAME = "channel"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run{
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notificationButton = findViewById<Button>(R.id.btn_notification)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Software Inversion")
                .setContentText("hello my friends, welcome!")
                .setSmallIcon(R.drawable.img_softwareinversion)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("this is a big text that don't fit in one line "))
                .setContentIntent(pendingIntent)
                .build()
        val notificationManager = NotificationManagerCompat.from(this)
        notificationButton.setOnClickListener {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = "describe channel we made"
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

// Build.VERSION.SDK_INT :
// The SDK version of the software currently running on this hardware device.