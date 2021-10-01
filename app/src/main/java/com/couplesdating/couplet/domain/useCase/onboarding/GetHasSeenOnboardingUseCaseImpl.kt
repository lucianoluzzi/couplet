package com.couplesdating.couplet.domain.useCase.onboarding

import com.couplesdating.couplet.data.repository.OnboardingRepository

class GetHasSeenOnboardingUseCaseImpl(
    private val onboardingRepository: OnboardingRepository
) : GetHasSeenOnboardingUseCase {
    override fun hasSeenOnboarding() = onboardingRepository.hasSeenOnboarding()
}