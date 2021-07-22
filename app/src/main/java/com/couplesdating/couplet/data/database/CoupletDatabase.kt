package com.couplesdating.couplet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.couplesdating.couplet.data.database.dao.CategoryDao
import com.couplesdating.couplet.data.database.dao.CategoryWithIdeasDao
import com.couplesdating.couplet.data.database.dao.IdeaDao
import com.couplesdating.couplet.data.database.entities.CategoryEntity
import com.couplesdating.couplet.data.database.entities.IdeaEntity

@Database(entities = [IdeaEntity::class, CategoryEntity::class], version = 1)
abstract class CoupletDatabase : RoomDatabase() {
    abstract fun ideaDao(): IdeaDao
    abstract fun categoryDao(): CategoryDao
    abstract fun categoriesWithIdeasDao(): CategoryWithIdeasDao
}