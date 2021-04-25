package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.AcceptedInvite

class GetAcceptedInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : GetAcceptedInviteUseCase {

    override fun getAcceptedInvite(): AcceptedInvite? =
        inviteRepository.getAcceptedPairInviteUser()
}