package com.couplesdating.couplet.domain.useCase.idea

import com.couplesdating.couplet.data.dataSource.CategoryLocalDataSource
import com.couplesdating.couplet.data.database.mapper.IdeaMapper
import com.couplesdating.couplet.domain.model.Idea

class RemoveIdeaUseCaseImpl(
    private val localDataSource: CategoryLocalDataSource
) : RemoveIdeaUseCase {

    override suspend fun removeIdea(idea: Idea, categoryId: String) {
        val ideaEntity = IdeaMapper().ideaToEntity(
            idea = idea,
            categoryId = categoryId
        )
        localDataSource.removeIdea(ideaEntity)
    }
}