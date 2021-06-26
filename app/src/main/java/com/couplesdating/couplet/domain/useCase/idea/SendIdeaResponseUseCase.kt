package com.couplesdating.couplet.domain.useCase.idea

import com.couplesdating.couplet.domain.model.UserResponse
import com.couplesdating.couplet.domain.network.Response

interface SendIdeaResponseUseCase {
    suspend fun sendIdeaResponse(
        ideaId: String,
        userResponse: UserResponse
    ) : Response
}