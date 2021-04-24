package com.couplesdating.couplet.domain.useCase

interface InviteExistsUseCase {
    suspend fun inviteExists(inviteId: String): Boolean
}