package com.couplesdating.couplet.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.couplesdating.couplet.data.database.entities.MatchIdeaEntity

@Dao
interface MatchIdeaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ideas: MatchIdeaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ideas: List<MatchIdeaEntity>)

    @Delete
    suspend fun delete(idea: MatchIdeaEntity)
}