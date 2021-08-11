package com.couplesdating.couplet.domain.useCase.match

import com.couplesdating.couplet.data.repository.MatchRepository
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.network.Response

class DeleteMatchUseCaseImpl(
    private val matchRepository: MatchRepository
) : DeleteMatchUseCase {

    override suspend fun deleteMatch(match: Match): Response {
        return matchRepository.deleteMatch(match.id)
    }
}