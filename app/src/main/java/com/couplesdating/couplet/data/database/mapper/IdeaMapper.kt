package com.couplesdating.couplet.data.database.mapper

import com.couplesdating.couplet.data.database.entities.IdeaEntity
import com.couplesdating.couplet.domain.model.Idea

class IdeaMapper {

    fun ideaToEntity(idea: Idea, categoryId: String) = IdeaEntity(
        id = idea.id,
        description = idea.description,
        categoryId = categoryId
    )

    fun ideaEntityToIdea(ideaEntity: IdeaEntity) = Idea(
        id = ideaEntity.id,
        description = ideaEntity.description
    )
}