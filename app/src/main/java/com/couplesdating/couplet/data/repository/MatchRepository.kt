package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.network.Response
import kotlinx.coroutines.flow.Flow

interface MatchRepository {
    suspend fun getNewMatches(): Flow<List<Match>>
    suspend fun deleteAllMatches(currentUserId: String): Response
    suspend fun deleteMatch(match: Match): Response
    suspend fun refreshMatches(currentUserId: String)
}