package com.couplesdating.couplet.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.couplesdating.couplet.data.database.entities.IdeaEntity

@Dao
interface IdeaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ideas: List<IdeaEntity>)

    @Delete
    suspend fun delete(idea: IdeaEntity)
}