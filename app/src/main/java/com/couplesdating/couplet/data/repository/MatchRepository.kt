package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Response

interface MatchRepository {
    suspend fun getNewMatches(currentUserId: String): Response
}