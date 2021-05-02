package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel

class GetReceivedInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : GetReceivedInviteUseCase {

    override suspend fun getReceivedInvite(currentUserId: String?): InviteModel? {
        return inviteRepository.getReceivedPairInvite(currentUserId)
    }
}