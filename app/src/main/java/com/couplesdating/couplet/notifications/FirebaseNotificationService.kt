package com.couplesdating.couplet.notifications

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showMatchActivity(message)
    }

    private fun showMatchActivity(message: RemoteMessage) {
        try {
            val activityToStart = Class.forName(ACTIVITY_TO_SHOW)
            val intent = Intent(applicationContext, activityToStart).apply {
                putExtra(NOTIFICATION_KEY, true)
            }
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
            applicationContext.startActivity(intent)
        } catch (exception: Exception) {
            Log.e("NotificationService", exception.message ?: exception.toString())
        }
    }

    companion object {
        private const val ACTIVITY_TO_SHOW = "com.couplesdating.couplet.ui.match.OverlayMatchActivity"
        const val NOTIFICATION_KEY = "NOTIFICATION"
    }
}