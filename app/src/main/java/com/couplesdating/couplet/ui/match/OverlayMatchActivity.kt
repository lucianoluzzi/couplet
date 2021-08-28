package com.couplesdating.couplet.ui.match

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.couplesdating.couplet.R
import com.couplesdating.couplet.notifications.FirebaseNotificationService.Companion.MESSAGE_LABEL
import com.couplesdating.couplet.notifications.FirebaseNotificationService.Companion.NOTIFICATION_MESSAGE

class OverlayMatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_overlay)
    }

    override fun onStart() {
        super.onStart()
        val notificationMessage = intent.getStringExtra(NOTIFICATION_MESSAGE)
        val notificationLabel = intent.getStringExtra(MESSAGE_LABEL)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, OverlayMatchFragment().apply {
                arguments = bundleOf(
                    NOTIFICATION_MESSAGE to notificationMessage,
                    MESSAGE_LABEL to notificationLabel
                )
            })
            .commit()
    }
}