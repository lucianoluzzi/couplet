package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel
import java.lang.Exception

class GetReceivedInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : GetReceivedInviteUseCase {

    override suspend fun getReceivedInvite(currentUserId: String?): InviteModel? {
        return try {
            inviteRepository.getReceivedPairInvite(currentUserId)
        } catch (exception: Exception) {
            null
        }
    }
}