package com.couplesdating.couplet.ui.match

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.couplesdating.couplet.R
import com.couplesdating.couplet.notifications.FirebaseNotificationService

class OverlayMatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_overlay)
    }

    override fun onStart() {
        super.onStart()
        val notificationMessage =
            intent.getStringExtra(FirebaseNotificationService.NOTIFICATION_MESSAGE)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, OverlayMatchFragment().apply {
                arguments = bundleOf(
                    FirebaseNotificationService.NOTIFICATION_MESSAGE to notificationMessage
                )
            })
            .commit()
    }
}