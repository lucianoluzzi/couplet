package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.model.User

interface CreateInviteUseCase {
    suspend fun createInvite(currentUser: User): Response
}