package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.domain.model.AcceptedInvite

interface DeleteInviteUseCase {
    suspend fun deleteInvite(acceptedInvite: AcceptedInvite)
}