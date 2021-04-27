package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Response

interface CategoryRepository {
    suspend fun getCategories(): Response
}