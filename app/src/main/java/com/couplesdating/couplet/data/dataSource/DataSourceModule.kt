package com.couplesdating.couplet.data.dataSource

import com.couplesdating.couplet.data.dataSource.category.CategoryLocalDataSource
import com.couplesdating.couplet.data.dataSource.category.CategoryRemoteDataSource
import com.couplesdating.couplet.data.dataSource.match.MatchLocalDataSource
import com.couplesdating.couplet.data.dataSource.match.MatchRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single {
        CategoryRemoteDataSource(
            service = get()
        )
    }

    single {
        CategoryLocalDataSource(
            categoryWithIdeasDao = get(),
            categoryDao = get(),
            ideaDao = get()
        )
    }

    single {
        MatchRemoteDataSource(
            database = get()
        )
    }

    single {
        MatchLocalDataSource(
            matchDao = get(),
            matchWithIdeaDao = get(),
            ideaDao = get()
        )
    }
}