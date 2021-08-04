package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel

class DeleteInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : DeleteInviteUseCase {

    override suspend fun deleteInvite(invite: InviteModel) {
        inviteRepository.deletePairInvite(invite.inviteId)
    }

    override suspend fun deleteInvite(inviteId: String) {
        inviteRepository.deletePairInvite(inviteId)
    }
}