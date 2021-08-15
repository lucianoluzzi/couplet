package com.couplesdating.couplet.domain.useCase.match

import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.network.Response
import kotlinx.coroutines.flow.Flow

interface GetNewMatchesUseCase {
    suspend fun getNewMatches(currentUserId: String): Response
    suspend fun listenToMatches(): Flow<List<Match>>
}