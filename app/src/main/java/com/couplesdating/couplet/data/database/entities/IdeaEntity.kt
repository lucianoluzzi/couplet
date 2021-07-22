package com.couplesdating.couplet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "idea")
data class IdeaEntity(
    @PrimaryKey val id: String,
    val description: String,
    @ColumnInfo(name = "category_id") val categoryId: String
)