package com.couplesdating.couplet.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.couplesdating.couplet.data.database.entities.MatchWithIdeaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchWithIdeaDao {

    @Transaction
    @Query("SELECT * FROM `match`")
    fun getMatchesWithIdeas(): Flow<List<MatchWithIdeaEntity>>
}