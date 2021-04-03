package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.PairRepository

class AcceptUserInviteUseCaseImpl(
    private val pairRepository: PairRepository
) : AcceptUserInviteUseCase {

    override fun acceptUserInvite(userId: String) {
        pairRepository.saveAcceptedPairInvite(userId)
    }
}