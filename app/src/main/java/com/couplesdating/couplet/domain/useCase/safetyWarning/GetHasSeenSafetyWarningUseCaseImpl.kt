package com.couplesdating.couplet.domain.useCase.safetyWarning

import com.couplesdating.couplet.data.repository.SafetyWarningRepository

class GetHasSeenSafetyWarningUseCaseImpl(
    private val safetyWarningRepository: SafetyWarningRepository
) : GetHasSeenSafetyWarningUseCase {
    override fun hasSeenSafetyWarning() = safetyWarningRepository.hasSeenSafetyWarning()
}