package com.couplesdating.couplet.notifications

import android.app.ActivityOptions
import android.content.Intent
import android.util.Log
import com.couplesdating.couplet.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showMatchActivity(message)
    }

    private fun showMatchActivity(message: RemoteMessage) {
        try {
            Log.d("NOTIFICATION", "Remote message data: ${message.data}")
            val messageType = message.data["type"]
            Log.d("NOTIFICATION", "Message type: $messageType")
            if (messageType != null && messageType == "MATCH") {
                Log.d("NOTIFICATION", "Message body: ${message.notification?.body}")
                val messageBody = message.notification?.body ?: "Seems both of you are into this idea \uD83D\uDE08"
                val activityToStart = Class.forName(ACTIVITY_TO_SHOW)
                val intent = Intent(applicationContext, activityToStart).apply {
                    putExtra(NOTIFICATION_MESSAGE, messageBody)
                }
                intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
                applicationContext.startActivity(
                    intent,
                    ActivityOptions.makeCustomAnimation(
                        applicationContext,
                        R.anim.slide_in_bottom,
                        android.R.anim.fade_out
                    ).toBundle()
                )
            }
        } catch (exception: Exception) {
            Log.e("NotificationService", exception.message ?: exception.toString())
        }
    }

    companion object {
        private const val ACTIVITY_TO_SHOW =
            "com.couplesdating.couplet.ui.match.OverlayMatchActivity"
        const val NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE"
    }
}