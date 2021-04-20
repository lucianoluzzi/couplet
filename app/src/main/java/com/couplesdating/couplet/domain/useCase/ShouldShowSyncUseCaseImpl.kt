package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.PairRepository

class ShouldShowSyncUseCaseImpl(
    private val pairRepository: PairRepository
) : ShouldShowSyncUseCase {

    override fun invoke() = pairRepository.shouldShowSync()
}