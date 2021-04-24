package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.PairRepository
import com.couplesdating.couplet.domain.model.AcceptedInvite

class GetAcceptedInviteUseCaseImpl(
    private val pairRepository: PairRepository
) : GetAcceptedInviteUseCase {

    override fun getAcceptedInvite(): AcceptedInvite? = pairRepository.getAcceptedPairInviteUser()
}