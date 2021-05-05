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

    single<PairRepository> { PairRepositoryImpl(get(), get()) }

    single<InviteRepository> { InviteRepositoryImpl(get(), get()) }

    single<CategoryRepository> { CategoryRepositoryImpl() }

    single<MatchRepository> { MatchRepositoryImpl(get()) }
}