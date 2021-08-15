package com.couplesdating.couplet.domain.useCase.idea

import com.couplesdating.couplet.data.dataSource.category.CategoryLocalDataSource
import com.couplesdating.couplet.data.database.mapper.IdeaMapper
import com.couplesdating.couplet.domain.model.Idea

class RemoveIdeaUseCaseImpl(
    private val localDataSource: CategoryLocalDataSource
) : RemoveIdeaUseCase {

    override suspend fun removeIdea(idea: Idea) {
        val ideaEntity = IdeaMapper().ideaToEntity(
            idea = idea
        )
        localDataSource.removeIdea(ideaEntity)
    }
}