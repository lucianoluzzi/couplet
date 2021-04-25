package com.couplesdating.couplet.domain.useCase.invite

interface AcceptUserInviteUseCase {
    fun acceptUserInvite(inviteId: String, userId: String)
}