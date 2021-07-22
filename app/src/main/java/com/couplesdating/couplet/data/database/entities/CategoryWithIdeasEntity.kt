package com.couplesdating.couplet.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithIdeasEntity(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val ideas: List<IdeaEntity>
)
