package com.couplesdating.couplet.data.dataSource

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
}