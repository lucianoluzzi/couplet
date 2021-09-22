package com.couplesdating.couplet.notifications

import android.app.ActivityOptions
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import com.couplesdating.couplet.R
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject

class FirebaseNotificationService : FirebaseMessagingService() {
    private val applicationLifecycleObserver by inject<AppLifecycleObserver>()

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("NOTIFICATION", "received")
        super.onMessageReceived(message)
        if (applicationLifecycleObserver.isOpen) {
            showMatchActivity(message)
        } else {
            createNotification(message)
        }
    }

    private fun showMatchActivity(message: RemoteMessage) {
        try {
            val messageType = message.data["type"]
            if (messageType != null && messageType == "MATCH") {
                val messageBody = message.data["body"]
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

    private fun createNotification(message: RemoteMessage) {
        val messageData = message.data
        messageData["body"]?.let { messageBody ->
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(applicationContext, 0, getMainActivityIntent(), 0)
            val builder = NotificationCompat.Builder(applicationContext, "COUPLET_CHANNEL")
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentTitle(messageData["title"] ?: "Couplet")
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setGroup(messageData["label"])
            NotificationManagerCompat.from(applicationContext).notify(0, builder.build())
        }
    }

    private fun getMainActivityIntent(): Intent {
        val activityToStart = Class.forName(MAIN_ACTIVITY)
        return Intent(applicationContext, activityToStart)
    }

    companion object {
        private const val ACTIVITY_TO_SHOW =
            "com.couplesdating.couplet.ui.match.OverlayMatchActivity"
        private const val MAIN_ACTIVITY = "com.couplesdating.couplet.MainActivity"
        const val NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE"
        const val MESSAGE_LABEL = "MESSAGE_LABEL"
    }
}