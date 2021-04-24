package com.couplesdating.couplet.domain.useCase

interface AcceptUserInviteUseCase {
    fun acceptUserInvite(inviteId: String, userId: String)
}