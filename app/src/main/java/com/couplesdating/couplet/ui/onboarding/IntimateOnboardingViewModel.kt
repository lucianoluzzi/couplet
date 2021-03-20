package com.couplesdating.couplet.ui.onboarding

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.IntimateOnboadingEvents

class IntimateOnboardingViewModel(
    private val analytics: Analytics
) : ViewModel() {

    fun onHellYeahClicked() {
        analytics.trackEvent(IntimateOnboadingEvents.HellYeahClicked)
    }
}