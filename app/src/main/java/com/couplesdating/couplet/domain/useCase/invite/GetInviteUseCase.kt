package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.domain.model.InviteModel

interface GetInviteUseCase {
    suspend fun getInvite(currentUserId: String? = null): InviteModel?
}