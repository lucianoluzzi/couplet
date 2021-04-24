package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.PairRepository

class InviteExistsUseCaseImpl(
    private val pairRepository: PairRepository
) : InviteExistsUseCase {

    override suspend fun inviteExists(inviteId: String): Boolean {
        return try {
            pairRepository.inviteExists(inviteId)
        } catch (exception: Exception) {
            false
        }
    }
}