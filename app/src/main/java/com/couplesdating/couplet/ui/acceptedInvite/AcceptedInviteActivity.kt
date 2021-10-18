package com.couplesdating.couplet.ui.acceptedInvite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.couplesdating.couplet.R

class AcceptedInviteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner_accepted)
    }

    override fun onStart() {
        super.onStart()
        intent?.getStringExtra("pair_info")?.let {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, PartnerAcceptedInviteFragment().apply {
                    arguments = bundleOf(
                        "pair_info" to it,
                        "pair_info_activity" to true
                    )
                })
                .commit()
        }
    }
}