package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.FirebaseAnalyticsTracker
import org.koin.dsl.module

val repositoryModule = module {
    single<Analytics> { FirebaseAnalyticsTracker(get()) }

    single<UserRepository> {
        UserRepositoryImpl(
            database = get(),
            authenticator = get()
        )
    }

    single<PairRepository> {
        PairRepositoryImpl(
            database = get(),
            preferences = get(),
            service = get()
        )
    }

    single<InviteRepository> { InviteRepositoryImpl(get(), get()) }

    single<CategoryRepository> {
        CategoryRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }

    single<MatchRepository> {
        MatchRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }

    single<IdeaRepository> { IdeaRepositoryImpl(get()) }
}