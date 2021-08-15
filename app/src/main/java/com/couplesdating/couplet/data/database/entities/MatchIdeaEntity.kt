package com.couplesdating.couplet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "match_idea")
data class MatchIdeaEntity(
    @PrimaryKey val id: String,
    val description: String,
    @ColumnInfo(name = "category_id") val categoryId: String
)