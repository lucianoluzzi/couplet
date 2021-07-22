package com.couplesdating.couplet.data.database.dao

import com.couplesdating.couplet.data.database.CoupletDatabase
import org.koin.dsl.module

val daoModule = module {
    single {
        get<CoupletDatabase>().categoriesWithIdeasDao()
    }

    single {
        get<CoupletDatabase>().categoryDao()
    }

    single {
        get<CoupletDatabase>().ideaDao()
    }
}