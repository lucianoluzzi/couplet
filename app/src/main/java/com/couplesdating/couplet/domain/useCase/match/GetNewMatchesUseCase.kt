package com.couplesdating.couplet.domain.useCase.match

import com.couplesdating.couplet.domain.model.Response

interface GetNewMatchesUseCase {
    suspend fun getNewMatches(currentUserId: String): Response
}