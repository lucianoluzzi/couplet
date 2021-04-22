package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.PairRepository

class GetPartnerUseCaseImpl(
    private val pairRepository: PairRepository
) : GetPartnerUseCase {

    override suspend fun getPartner(currentUserId: String) =
        pairRepository.getPartner(currentUserId)
}