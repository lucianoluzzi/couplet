package com.couplesdating.couplet.domain.useCase.category

import com.couplesdating.couplet.data.repository.CategoryRepository
import com.couplesdating.couplet.domain.model.Category
import com.couplesdating.couplet.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategoriesUseCaseImpl(
    private val categoriesRepository: CategoryRepository
) : GetCategoriesUseCase {

    override fun getCategories(userId: String): Flow<List<Category>> = flow {
        val response = categoriesRepository.getCategories(userId)
        if (response is Response.Success<*>) {
            emit(response.data as List<Category>)
        } else {
            emit(emptyList<Category>())
        }
    }
}