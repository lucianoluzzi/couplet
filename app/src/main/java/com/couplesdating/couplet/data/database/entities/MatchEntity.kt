package com.couplesdating.couplet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "match")
data class MatchEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "partner_response") val partnerResponse: String,
    @ColumnInfo(name = "idea_id") val ideaId: String
)