package com.couplesdating.couplet.domain.useCase.onboarding

import com.couplesdating.couplet.data.repository.OnboardingRepository

class SetOnboardingShownUseCaseImpl(
    private val onboardingRepository: OnboardingRepository
) : SetOnboardingShownUseCase {
    override fun setOnboardingShown() {
        onboardingRepository.setOnboardingShown()
    }
}