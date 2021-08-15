package com.couplesdating.couplet.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MatchWithIdeaEntity(
    @Embedded val match: MatchEntity,
    @Relation(
        parentColumn = "idea_id",
        entityColumn = "id"
    )
    val idea: MatchIdeaEntity
)
