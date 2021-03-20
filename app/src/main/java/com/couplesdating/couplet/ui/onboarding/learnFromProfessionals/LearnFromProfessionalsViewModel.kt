package com.couplesdating.couplet.ui.onboarding.learnFromProfessionals

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.onboarding.LearnFromProfessionalsEvents

class LearnFromProfessionalsViewModel(
    private val analytics: Analytics
) : ViewModel() {

    fun onContinueClicked() {
        analytics.trackEvent(LearnFromProfessionalsEvents.ContinueClicked)
    }
}