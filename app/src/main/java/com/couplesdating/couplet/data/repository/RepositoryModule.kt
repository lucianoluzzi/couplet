package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.FirebaseAnalyticsTracker
import org.koin.dsl.module

val repositoryModule = module {
    single<Analytics> { FirebaseAnalyticsTracker(get()) }

    single<UserRepository> { UserRepositoryImpl(get()) }

    single<PairRepository> { PairRepositoryImpl(get(), get()) }

    single<InviteRepository> { InviteRepositoryImpl(get(), get()) }

    single<CategoryRepository> { CategoryRepositoryImpl() }
}