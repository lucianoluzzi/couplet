package com.couplesdating.couplet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.couplesdating.couplet.data.database.dao.*
import com.couplesdating.couplet.data.database.entities.CategoryEntity
import com.couplesdating.couplet.data.database.entities.IdeaEntity
import com.couplesdating.couplet.data.database.entities.MatchEntity
import com.couplesdating.couplet.data.database.entities.MatchIdeaEntity

@Database(
    entities = [
        IdeaEntity::class,
        CategoryEntity::class,
        MatchEntity::class,
        MatchIdeaEntity::class
    ], version = 1
)
abstract class CoupletDatabase : RoomDatabase() {
    abstract fun ideaDao(): IdeaDao
    abstract fun categoryDao(): CategoryDao
    abstract fun categoriesWithIdeasDao(): CategoryWithIdeasDao
    abstract fun matchDao(): MatchDao
    abstract fun matchIdeaDao(): MatchIdeaDao
    abstract fun matchesWithIdeasDao(): MatchWithIdeaDao
}