package com.couplesdating.couplet.domain.useCase.match

import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.network.Response

interface DeleteAllMatchesUseCase {
    suspend fun deleteAllMatches(user: User): Response
}