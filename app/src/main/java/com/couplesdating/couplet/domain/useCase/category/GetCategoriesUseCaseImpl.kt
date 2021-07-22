package com.couplesdating.couplet.domain.useCase.category

import com.couplesdating.couplet.data.repository.CategoryRepository
import com.couplesdating.couplet.domain.model.Category
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.response.CategoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import java.util.*

class GetCategoriesUseCaseImpl(
    private val categoriesRepository: CategoryRepository
) : GetCategoriesUseCase {

    override suspend fun getCategories(userId: String): Flow<List<Category>> {
        val timeZone = TimeZone.getDefault().id
        return categoriesRepository.getCategories(userId, timeZone).map { categories ->
            categories.sortedBy { it.spiciness }
        }
    }
}