package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel

class GetInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : GetInviteUseCase {

    override suspend fun getInvite(currentUserId: String?): InviteModel? {
        return inviteRepository.getPairInvite(currentUserId)
    }
}