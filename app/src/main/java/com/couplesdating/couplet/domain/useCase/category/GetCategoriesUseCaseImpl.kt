package com.couplesdating.couplet.domain.useCase.category

import com.couplesdating.couplet.data.repository.CategoryRepository
import com.couplesdating.couplet.domain.model.Category
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.response.CategoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategoriesUseCaseImpl(
    private val categoriesRepository: CategoryRepository
) : GetCategoriesUseCase {

    override fun getCategories(userId: String): Flow<List<Category>> = flow {
        val response = categoriesRepository.getCategories(userId)
        if (response is Response.Success<*> && response.data is List<*>) {
            val categoryResponse = response.data as List<CategoryResponse>
            val categoriesWithIdeas = categoryResponse.map { categoryResponse ->
                Category(
                    id = categoryResponse.id,
                    title = categoryResponse.title,
                    description = categoryResponse.description,
                    isPremium = categoryResponse.isPremium,
                    spiciness = categoryResponse.spiciness,
                    newIdeas = categoryResponse.ideas
                )
            }.sortedBy {
                it.spiciness
            }
            emit(categoriesWithIdeas)
        } else {
            val emptyList = listOf<Category>()
            emit(emptyList)
        }
    }
}