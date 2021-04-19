package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User

interface CreateInviteUseCase {
    suspend fun createInvite(currentUser: User, inviteNote: String?): Response
}