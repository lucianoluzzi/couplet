package com.couplesdating.couplet.notifications

import android.content.Intent
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
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            applicationContext.startActivity(intent)
        } catch (exception: Exception) {

        }
    }

    companion object {
        private const val ACTIVITY_TO_SHOW = "com.couplesdating.couplet.MainActivity"
        const val NOTIFICATION_KEY = "NOTIFICATION"
    }
}