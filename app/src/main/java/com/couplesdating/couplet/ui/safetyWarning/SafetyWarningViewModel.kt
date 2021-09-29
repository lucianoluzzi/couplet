package com.couplesdating.couplet.ui.safetyWarning

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.safetyWarning.SafetyWarningEvents
import com.couplesdating.couplet.domain.useCase.safetyWarning.SetHasSeenSafetyWarningUseCase

class SafetyWarningViewModel(
    setHasSeenSafetyWarningUseCase: SetHasSeenSafetyWarningUseCase,
    private val analytics: Analytics
) : ViewModel() {

    init {
        setHasSeenSafetyWarningUseCase.setHasSeenSafetyWarning()
    }

    fun onGoBackClicked() {
        analytics.trackEvent(SafetyWarningEvents.GoBackClicked)
    }

    fun onContinueClicked() {
        analytics.trackEvent(SafetyWarningEvents.ContinueClicked)
    }
}