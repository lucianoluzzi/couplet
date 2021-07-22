package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(userId: String, timeZone: String): Flow<List<Category>>
}