package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Response

interface PairRepository {
    suspend fun formPair(firstUserId: String, secondUserId: String): Response
}