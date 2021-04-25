package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.data.repository.InviteRepository

class InviteExistsUseCaseImpl(
    private val inviteRepository: InviteRepository
) : InviteExistsUseCase {

    override suspend fun inviteExists(inviteId: String): Boolean {
        return try {
            inviteRepository.inviteExists(inviteId)
        } catch (exception: Exception) {
            false
        }
    }
}