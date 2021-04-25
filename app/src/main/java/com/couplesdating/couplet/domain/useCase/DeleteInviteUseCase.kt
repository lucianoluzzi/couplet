package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.domain.model.AcceptedInvite

interface DeleteInviteUseCase {
    suspend fun deleteInvite(acceptedInvite: AcceptedInvite)
}