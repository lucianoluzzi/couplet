package com.couplesdating.couplet.domain.useCase.pair

import com.couplesdating.couplet.data.repository.PairRepository

class SetSyncShownUseCaseImpl(
    private val pairRepository: PairRepository
) : SetSyncShownUseCase {

    override fun invoke() {
        pairRepository.setSyncPageShown()
    }
}