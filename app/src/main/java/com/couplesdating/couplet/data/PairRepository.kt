package com.couplesdating.couplet.data

import com.couplesdating.couplet.domain.model.Response

interface PairRepository {
    suspend fun formPair(firstUserId: String, secondUserId: String): Response
}