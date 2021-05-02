package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.domain.model.InviteModel

interface GetReceivedInviteUseCase {
    suspend fun getReceivedInvite(currentUserId: String? = null): InviteModel?
}