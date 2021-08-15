package com.couplesdating.couplet.data.database.mapper

import com.couplesdating.couplet.data.database.entities.MatchIdeaEntity
import com.couplesdating.couplet.domain.model.Idea

class MatchIdeaMapper {
    fun ideaToEntity(idea: Idea) = MatchIdeaEntity(
        id = idea.id,
        description = idea.description,
        categoryId = idea.categoryId
    )

    fun ideaEntityToIdea(
        ideaEntity: MatchIdeaEntity,
        categoryId: String,
    ) = Idea(
        id = ideaEntity.id,
        description = ideaEntity.description,
        categoryId = categoryId
    )
}