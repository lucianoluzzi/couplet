package com.couplesdating.couplet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.couplesdating.couplet.data.database.entities.CategoryWithIdeasEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryWithIdeasDao {

    @Transaction
    @Query("SELECT * FROM category")
    fun getCategoriesWithIdeas(): Flow<List<CategoryWithIdeasEntity>>
}