package com.couplesdating.couplet.domain.useCase.category

import com.couplesdating.couplet.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface GetCategoriesUseCase {
    suspend fun getCategories(): Flow<List<Category>>
}