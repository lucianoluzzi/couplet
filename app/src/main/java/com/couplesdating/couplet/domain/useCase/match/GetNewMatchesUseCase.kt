package com.couplesdating.couplet.domain.useCase.match

import com.couplesdating.couplet.domain.network.Response

interface GetNewMatchesUseCase {
    suspend fun getNewMatches(currentUserId: String): Response
}