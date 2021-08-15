package com.couplesdating.couplet.data.database.dao

import androidx.room.*
import com.couplesdating.couplet.data.database.entities.MatchEntity

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(matches: MatchEntity)

    @Delete
    suspend fun remove(match: MatchEntity)

    @Query("DELETE FROM `match`")
    suspend fun removeAll()
}