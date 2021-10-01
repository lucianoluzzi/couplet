package com.couplesdating.couplet.ui.onboarding.privacy

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.onboarding.PrivacyEvents
import com.couplesdating.couplet.domain.useCase.onboarding.SetOnboardingShownUseCase

class PrivacyViewModel(
    private val setOnboardingShownUseCase: SetOnboardingShownUseCase,
    private val analytics: Analytics
) : ViewModel() {

    fun onContinueClicked() {
        setOnboardingShown()
        analytics.trackEvent(PrivacyEvents.ContinueClicked)
    }

    private fun setOnboardingShown() {
        setOnboardingShownUseCase.setOnboardingShown()
    }
}