package com.couplesdating.couplet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val spiciness: Int,
    @ColumnInfo(name = "is_premium") val isPremium: Boolean
)
