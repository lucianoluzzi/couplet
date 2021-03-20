package com.couplesdating.couplet.ui.onboarding.privacy

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.onboarding.PrivacyEvents

class PrivacyViewModel(
    private val analytics: Analytics
) : ViewModel() {

    fun onContinueClicked() {
        analytics.trackEvent(PrivacyEvents.ContinueClicked)
    }
}