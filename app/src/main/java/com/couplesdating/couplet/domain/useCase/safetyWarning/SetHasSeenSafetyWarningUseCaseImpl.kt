package com.couplesdating.couplet.domain.useCase.safetyWarning

import com.couplesdating.couplet.data.repository.SafetyWarningRepository

class SetHasSeenSafetyWarningUseCaseImpl(
    private val safetyWarningRepository: SafetyWarningRepository
) : SetHasSeenSafetyWarningUseCase {

    override fun setHasSeenSafetyWarning() {
        safetyWarningRepository.setHasSeenSafetyWarning()
    }
}