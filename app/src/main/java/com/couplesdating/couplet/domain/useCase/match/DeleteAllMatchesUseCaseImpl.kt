package com.couplesdating.couplet.domain.useCase.match

import com.couplesdating.couplet.data.repository.MatchRepository
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.network.Response

class DeleteAllMatchesUseCaseImpl(
    private val matchRepository: MatchRepository
) : DeleteAllMatchesUseCase {

    override suspend fun deleteAllMatches(user: User): Response {
        return matchRepository.deleteAllMatches(user.userId)
    }
}