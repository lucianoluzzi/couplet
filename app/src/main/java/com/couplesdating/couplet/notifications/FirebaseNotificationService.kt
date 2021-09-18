package com.couplesdating.couplet.notifications

import android.app.ActivityOptions
import android.content.Intent
import android.util.Log
import androidx.core.os.bundleOf
import com.couplesdating.couplet.R
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showMatchActivity(message)
    }

    private fun showMatchActivity(message: RemoteMessage) {
        try {
            val messageType = message.data["type"]
            if (messageType != null && messageType == "MATCH") {
                val messageBody = message.notification?.body
                    ?: "Seems both of you are into this idea \uD83D\uDE08"
                val activityToStart = Class.forName(ACTIVITY_TO_SHOW)

                val intent = Intent(applicationContext, activityToStart).apply {
                    putExtras(
                        bundleOf(
                            NOTIFICATION_MESSAGE to messageBody,
                            MESSAGE_LABEL to message.data["label"]
                        )
                    )
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
            FirebaseCrashlytics.getInstance().recordException(exception)
            Log.e("NotificationService", exception.message ?: exception.toString())
        }
    }

    companion object {
        private const val ACTIVITY_TO_SHOW =
            "com.couplesdating.couplet.ui.match.OverlayMatchActivity"
        const val NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE"
        const val MESSAGE_LABEL = "MESSAGE_LABEL"
    }
}