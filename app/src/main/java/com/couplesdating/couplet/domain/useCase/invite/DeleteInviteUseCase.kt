package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.domain.model.InviteModel

interface DeleteInviteUseCase {
    suspend fun deleteInvite(invite: InviteModel)
}