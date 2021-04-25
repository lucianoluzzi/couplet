package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.AcceptedInvite

class DeleteInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : DeleteInviteUseCase {

    override suspend fun deleteInvite(acceptedInvite: AcceptedInvite) {
        inviteRepository.deletePairInvite(acceptedInvite.inviteId)
    }
}