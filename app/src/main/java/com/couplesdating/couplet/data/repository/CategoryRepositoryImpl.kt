package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.data.dataSource.CategoryLocalDataSource
import com.couplesdating.couplet.data.dataSource.CategoryRemoteDataSource
import com.couplesdating.couplet.data.database.mapper.CategoryMapper
import com.couplesdating.couplet.domain.model.Category
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.response.CategoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val remoteDataSource: CategoryRemoteDataSource,
    private val localDataSource: CategoryLocalDataSource
) : CategoryRepository {

    override suspend fun getCategories(): Flow<List<Category>> {
        return localDataSource.getCategories().map {
            CategoryMapper().categoriesWithIdeasEntityToCategories(it)
        }
    }

    override suspend fun refreshCategoriesWithIdeas(userId: String, timeZone: String) {
            val response = remoteDataSource.getCategories(userId, timeZone)
            if (response is Response.Success<*>) {
                val categoriesResponse = response.data as List<CategoryResponse>
                val entities = CategoryMapper().categoriesResponseToCategoriesWithIdeasEntity(
                    categoriesResponse
                )
                localDataSource.insert(entities)
            }
        }
}