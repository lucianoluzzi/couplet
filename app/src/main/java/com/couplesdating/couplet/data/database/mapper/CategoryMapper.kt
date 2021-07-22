package com.couplesdating.couplet.data.database.mapper

import com.couplesdating.couplet.data.database.entities.CategoryEntity
import com.couplesdating.couplet.data.database.entities.CategoryWithIdeasEntity
import com.couplesdating.couplet.domain.model.Category
import com.couplesdating.couplet.domain.response.CategoryResponse

class CategoryMapper {

    fun categoriesWithIdeasEntityToCategories(categoriesEntity: List<CategoryWithIdeasEntity>): List<Category> {
        return categoriesEntity.map {
            categoryWithIdeasEntityToCategory(it)
        }
    }

    private fun categoryWithIdeasEntityToCategory(categoryWithIdeasEntity: CategoryWithIdeasEntity): Category {
        val category = categoryWithIdeasEntity.category
        return Category(
            id = category.id,
            title = category.title,
            description = category.description,
            isPremium = category.isPremium,
            spiciness = category.spiciness,
            newIdeas = categoryWithIdeasEntity.ideas.map {
                IdeaMapper().ideaEntityToIdea(it)
            }
        )
    }

    fun categoriesResponseToCategoriesWithIdeasEntity(categoriesResponse: List<CategoryResponse>): List<CategoryWithIdeasEntity> {
        return categoriesResponse.map { categoryResponse ->
            CategoryWithIdeasEntity(
                ideas = categoryResponse.ideas.map {
                    IdeaMapper().ideaToEntity(
                        idea = it,
                        categoryId = categoryResponse.id
                    )
                },
                category = CategoryEntity(
                    id = categoryResponse.id,
                    title = categoryResponse.title,
                    description = categoryResponse.description,
                    spiciness = categoryResponse.spiciness,
                    isPremium = categoryResponse.isPremium
                )
            )
        }
    }
}