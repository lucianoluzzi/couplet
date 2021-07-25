package com.couplesdating.couplet.domain.useCase.category

interface RefreshCategoriesUseCase {
    suspend fun refreshCategories(userId: String)
}