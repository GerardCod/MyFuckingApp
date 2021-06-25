package com.example.mobileapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat

class NotificationDetailsActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notification_details)

    val sequence: CharSequence? = getMessageText(intent)
    val stringReply: String = sequence!!.toString()

    processMessage(stringReply)
  }

  private fun getMessageText(intent: Intent): CharSequence? {
    val remoteInput: Bundle? = RemoteInput.getResultsFromIntent(intent)

    return remoteInput?.getCharSequence(NotificationUtils.extraVoiceReply)
  }

  private fun processMessage(message: String) {
    if (message.equals("yes", true)) {
      //TODO
      openMaps()
    } else if (message.equals("no", true)) {
      //TODO
      sendSMS("Hola profe, soy Gerardo ya quedó la actividad", "2381119229")
    } else if (message.equals("maybe", true)) {
      //TODO
      Toast.makeText(this, "Aún no lo sé", Toast.LENGTH_LONG).show()
    }
  }

  private fun sendSMS(message: String, destination: String) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
      val manager: SmsManager = SmsManager.getDefault()
      manager.sendTextMessage(destination, null, message, null, null)
    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(arrayOf(Manifest.permission.SEND_SMS), 0)
      }
    }
  }

  private fun openMaps() {
    val mapsIntent: Intent = Intent(Intent.ACTION_VIEW)
    mapsIntent.setData(Uri.parse("geo:40.758895,-73.985131"))
    startActivity(mapsIntent)
  }
}