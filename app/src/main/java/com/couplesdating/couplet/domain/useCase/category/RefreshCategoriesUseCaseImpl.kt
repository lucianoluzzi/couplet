package com.couplesdating.couplet.domain.useCase.category

import com.couplesdating.couplet.data.repository.CategoryRepository
import java.util.*

class RefreshCategoriesUseCaseImpl(
    private val categoryRepository: CategoryRepository
) : RefreshCategoriesUseCase {
    override suspend fun refreshCategories(userId: String) {
        val timeZone = TimeZone.getDefault().id
        categoryRepository.refreshCategoriesWithIdeas(
            userId = userId,
            timeZone = timeZone
        )
    }
}