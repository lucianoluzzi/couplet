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
        Log.d("NOTIFICATION", "type: ${message.data["type"]}")
        super.onMessageReceived(message)
        message.data["type"]?.let {
            if (it == "MATCH") {
                showMatchActivityOrCreateNotification(message)
            } else if (it == "PAIR") {
                showPartnerAcceptedInviteActivityOrNotification(message)
            }
        }
    }

    private fun showPartnerAcceptedInviteActivityOrNotification(message: RemoteMessage) {
        if (applicationLifecycleObserver.isOpen) {
            val activityToStart = Class.forName(PARTNER_ACCEPTED_INVITE_ACTIVITY)

            val pairInfo = message.data["pair_info"] ?: "Your partner has accepted your invite"
            val arguments = bundleOf(
                "pair_info" to pairInfo
            )
            val intent = Intent(applicationContext, activityToStart).apply {
                putExtras(arguments)
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
        } else {
            createPairNotification(message)
        }
    }

    private fun showMatchActivityOrCreateNotification(message: RemoteMessage) {
        if (applicationLifecycleObserver.isOpen) {
            showMatchActivity(message)
        } else {
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(applicationContext, 0, getIntent(MAIN_ACTIVITY), 0)
            createNotification(
                pendingIntent = pendingIntent,
                message = message
            )
        }
    }

    private fun createPairNotification(message: RemoteMessage) {
        val pairInfo = message.data["pair_info"] ?: "Your partner has accepted your invite"
        val arguments = bundleOf(
            "pair_info" to pairInfo
        )
        val mainActivityIntent = getIntent(MAIN_ACTIVITY).apply {
            putExtras(arguments)
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(applicationContext, 0, mainActivityIntent, 0)
        createNotification(
            message = message,
            pendingIntent = pendingIntent
        )
    }

    private fun showMatchActivity(message: RemoteMessage) {
        try {
            createMatchNotification(message)
        } catch (exception: Exception) {
            FirebaseCrashlytics.getInstance().recordException(exception)
            Log.e("NotificationService", exception.message ?: exception.toString())
        }
    }

    private fun createMatchNotification(message: RemoteMessage) {
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

    private fun createNotification(
        message: RemoteMessage,
        pendingIntent: PendingIntent
    ) {
        val messageData = message.data
        messageData["body"]?.let { messageBody ->
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

    private fun getIntent(classToShow: String): Intent {
        val activityToStart = Class.forName(classToShow)
        return Intent(applicationContext, activityToStart)
    }

    companion object {
        private const val ACTIVITY_TO_SHOW =
            "com.couplesdating.couplet.ui.match.OverlayMatchActivity"
        private const val MAIN_ACTIVITY = "com.couplesdating.couplet.MainActivity"
        private const val PARTNER_ACCEPTED_INVITE_ACTIVITY =
            "com.couplesdating.couplet.ui.acceptedInvite.AcceptedInviteActivity"
        const val NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE"
        const val MESSAGE_LABEL = "MESSAGE_LABEL"
    }
}