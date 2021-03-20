package com.couplesdating.couplet.ui.onboarding.letsStart

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.onboarding.LetsStartEvents

class LetsStartViewModel(
    private val analytics: Analytics
) : ViewModel() {

    fun onLetsStartClicked() {
        analytics.trackEvent(LetsStartEvents.LetsStartClicked)
    }
}