package com.couplesdating.couplet.domain.useCase.idea

import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.domain.model.UserResponse
import com.couplesdating.couplet.domain.network.Response

interface SendIdeaResponseUseCase {
    suspend fun sendIdeaResponse(
        idea: Idea,
        userResponse: UserResponse
    ) : Response
}