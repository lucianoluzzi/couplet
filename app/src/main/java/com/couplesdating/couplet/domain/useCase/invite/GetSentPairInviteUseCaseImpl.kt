package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel

class GetSentPairInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : GetSentPairInviteUseCase {

    override suspend fun getSentPairInvite(currentUserId: String): InviteModel? {
        return try {
            inviteRepository.getSentPairInvite(currentUserId)
        } catch (exception: Exception) {
            null
        }
    }
}