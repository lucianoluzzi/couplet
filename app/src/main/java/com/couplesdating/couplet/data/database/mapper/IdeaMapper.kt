package com.couplesdating.couplet.data.database.mapper

import com.couplesdating.couplet.data.database.entities.IdeaEntity
import com.couplesdating.couplet.domain.model.Idea

class IdeaMapper {

    fun ideaToEntity(idea: Idea) = IdeaEntity(
        id = idea.id,
        description = idea.description,
        categoryId = idea.categoryId
    )

    fun ideaEntityToIdea(
        ideaEntity: IdeaEntity,
        categoryId: String,
    ) = Idea(
        id = ideaEntity.id,
        description = ideaEntity.description,
        categoryId = categoryId
    )
}