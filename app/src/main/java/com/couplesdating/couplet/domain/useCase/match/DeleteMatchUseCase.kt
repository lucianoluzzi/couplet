package com.couplesdating.couplet.domain.useCase.match

import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.network.Response

interface DeleteMatchUseCase {
    suspend fun deleteMatch(match: Match): Response
}