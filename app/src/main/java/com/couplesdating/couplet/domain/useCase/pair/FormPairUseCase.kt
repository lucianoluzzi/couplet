package com.couplesdating.couplet.domain.useCase.pair

import com.couplesdating.couplet.domain.model.Response

interface FormPairUseCase {
    suspend fun formPair(partnerId: String): Response
}