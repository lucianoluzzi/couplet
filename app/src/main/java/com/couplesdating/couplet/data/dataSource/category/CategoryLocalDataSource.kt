package com.couplesdating.couplet.data.dataSource.category

import com.couplesdating.couplet.data.database.dao.CategoryDao
import com.couplesdating.couplet.data.database.dao.CategoryWithIdeasDao
import com.couplesdating.couplet.data.database.dao.IdeaDao
import com.couplesdating.couplet.data.database.entities.CategoryWithIdeasEntity
import com.couplesdating.couplet.data.database.entities.IdeaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CategoryLocalDataSource(
    private val categoryWithIdeasDao: CategoryWithIdeasDao,
    private val categoryDao: CategoryDao,
    private val ideaDao: IdeaDao
) {
    suspend fun getCategories(): Flow<List<CategoryWithIdeasEntity>> = withContext(Dispatchers.IO) {
        return@withContext categoryWithIdeasDao.getCategoriesWithIdeas()
    }

    suspend fun insert(categories: List<CategoryWithIdeasEntity>) {
        categories.map { categoryWithIdeasEntity ->
            categoryDao.insertAll(categoryWithIdeasEntity.category)
            ideaDao.insertAll(categoryWithIdeasEntity.ideas)
        }
    }

    suspend fun removeIdea(ideaEntity: IdeaEntity) = withContext(Dispatchers.IO) {
        ideaDao.delete(ideaEntity)
    }
}