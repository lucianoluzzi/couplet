package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.request.SendIdeaRequest

interface IdeaRepository {
    suspend fun sendIdeaResponse(sendIdeaRequest: SendIdeaRequest): Response
}