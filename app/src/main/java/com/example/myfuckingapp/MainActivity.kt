package com.example.myfuckingapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.Wearable

class MainActivity : WearableActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Enables Always-on
    setAmbientEnabled()
  }

  fun notifyMe(view: View) {
    print("Notification fired")
    val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val NOTIFICATION_ID = "my_channel_id_01"

    val intent = Intent(this, NotificationDetails::class.java)
    val pendingIntent = PendingIntent.getActivities(this, 0, arrayOf(intent), PendingIntent.FLAG_UPDATE_CURRENT)

    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
      val notificationChannel = NotificationChannel(NOTIFICATION_ID, "My first notification", NotificationManager.IMPORTANCE_HIGH)
      notificationChannel.description = "Channel description"
      notificationChannel.enableLights(true)
      notificationChannel.lightColor = Color.CYAN
      notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
      notificationChannel.enableVibration(true)
      notificationManager.createNotificationChannel(notificationChannel)
    }

    val builder = NotificationCompat.Builder(this, NOTIFICATION_ID)
    builder.setAutoCancel(true)
      .setSmallIcon(R.drawable.ic_full_sad)
      .setContentTitle("Notification")
      .setContentText("My first notification")
      .setContentInfo("info")
      .setContentIntent(pendingIntent)

    notificationManager.notify(1, builder.build())
  }

  fun voiceInput(view: View) {
    val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val NOTIFICATION_ID = "my_channel_id_02"

    val intent = Intent(this, NotificationDetails::class.java)
    val pendingIntent = PendingIntent.getActivities(this, 0, arrayOf(intent), PendingIntent.FLAG_UPDATE_CURRENT)

    val replyLabel: String = getString(R.string.reply_label)
    val replyChoices: Array<String> = resources.getStringArray(R.array.reply_choices)
    val remoteInput: RemoteInput = RemoteInput.Builder(NotificationUtils.extraVoiceReply)
      .setLabel(replyLabel)
      .setChoices(replyChoices)
      .build()
    val notificationCompatBuilder: NotificationCompat.Action.Builder = NotificationCompat
      .Action.Builder(R.drawable.ic_expand_more_white_22, "My voice", pendingIntent)

    notificationCompatBuilder.addRemoteInput(remoteInput)
    val action: NotificationCompat.Action = notificationCompatBuilder.build()

    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
      val notificationChannel = NotificationChannel(NOTIFICATION_ID, "My first notification", NotificationManager.IMPORTANCE_HIGH)
      notificationChannel.description = "Channel description"
      notificationChannel.enableLights(true)
      notificationChannel.lightColor = Color.CYAN
      notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
      notificationChannel.enableVibration(true)
      notificationManager.createNotificationChannel(notificationChannel)
    }

    val builder = NotificationCompat.Builder(this, NOTIFICATION_ID)
    builder.setAutoCancel(true)
      .setSmallIcon(R.drawable.ic_full_sad)
      .setContentTitle("Notification")
      .setContentText("My first notification")
      .setContentInfo("info")
      .setContentIntent(pendingIntent)
      .addAction(action)
      .extend(NotificationCompat.WearableExtender().setContentAction(0))

    notificationManager.notify(1, builder.build())
  }
}