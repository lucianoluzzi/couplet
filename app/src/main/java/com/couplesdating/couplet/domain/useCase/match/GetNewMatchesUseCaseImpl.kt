package com.couplesdating.couplet.domain.useCase.match

import android.util.Log
import com.couplesdating.couplet.data.repository.MatchRepository
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.network.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class GetNewMatchesUseCaseImpl(
    private val matchRepository: MatchRepository
) : GetNewMatchesUseCase {

    override suspend fun getNewMatches(currentUserId: String): Response {
        return try {
            matchRepository.refreshMatches(currentUserId)
            val matches = matchRepository.getNewMatches().firstOrNull()
            Response.Success(matches)
        } catch (exception: Exception) {
            Log.e("MATCH", exception.toString())
            Response.Error(exception.message)
        }
    }

    override suspend fun listenToMatches(): Flow<List<Match>> {
        return matchRepository.getNewMatches()
    }
}