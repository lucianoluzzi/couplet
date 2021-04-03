package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.PairRepository

class GetAcceptedInviteUseCaseImpl(
    private val pairRepository: PairRepository
) : GetAcceptedInviteUseCase {

    override fun getAcceptedInviteUserId(): String? = pairRepository.getAcceptedPairInviteUser()
}