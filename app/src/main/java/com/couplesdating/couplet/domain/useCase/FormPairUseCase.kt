package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.domain.model.Response

interface FormPairUseCase {
    suspend fun formPair(partnerId: String): Response
}