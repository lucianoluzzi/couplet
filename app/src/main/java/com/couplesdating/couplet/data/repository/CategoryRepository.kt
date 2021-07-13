package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.network.Response

interface CategoryRepository {
    suspend fun getCategories(userId: String, timeZone: String): Response
}