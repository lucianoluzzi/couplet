package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.network.Response

interface MatchRepository {
    suspend fun getNewMatches(currentUserId: String): Response
    suspend fun deleteAllMatches(currentUserId: String): Response
}