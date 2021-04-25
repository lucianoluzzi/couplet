package com.couplesdating.couplet.domain.useCase.invite

interface InviteExistsUseCase {
    suspend fun inviteExists(inviteId: String): Boolean
}