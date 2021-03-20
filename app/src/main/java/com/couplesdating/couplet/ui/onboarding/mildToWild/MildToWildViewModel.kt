package com.couplesdating.couplet.ui.onboarding.mildToWild

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.MildToWildEvents

class MildToWildViewModel(
    private val analytics: Analytics
) : ViewModel() {

    fun onContinueClicked() {
        analytics.trackEvent(MildToWildEvents.ContinueClicked)
    }
}