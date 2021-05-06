package com.couplesdating.couplet.domain.useCase.match

import com.couplesdating.couplet.data.repository.MatchRepository
import com.couplesdating.couplet.domain.model.Response

class GetNewMatchesUseCaseImpl(
    private val matchRepository: MatchRepository
) : GetNewMatchesUseCase {

    override suspend fun getNewMatches(currentUserId: String): Response {
        return try {
            matchRepository.getNewMatches(currentUserId)
        } catch (exception: Exception) {
            Response.Error(exception.message)
        }
    }
}