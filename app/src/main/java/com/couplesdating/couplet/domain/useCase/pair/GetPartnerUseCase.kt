package com.couplesdating.couplet.domain.useCase.pair

import com.couplesdating.couplet.domain.model.Response

interface GetPartnerUseCase {
    suspend fun getPartner(currentUserId: String): Response
}