package com.couplesdating.couplet.ui.safetyWarning

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.safetyWarning.SafetyWarningEvents

class SafetyWarningViewModel(
    private val analytics: Analytics
) : ViewModel() {

    fun onGoBackClicked() {
        analytics.trackEvent(SafetyWarningEvents.GoBackClicked)
    }

    fun onContinueClicked() {
        analytics.trackEvent(SafetyWarningEvents.ContinueClicked)
    }
}