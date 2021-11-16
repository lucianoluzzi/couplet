package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.FirebaseAnalyticsTracker
import com.couplesdating.couplet.data.database.CoupletDatabase
import org.koin.dsl.module

val repositoryModule = module {
    single<Analytics> { FirebaseAnalyticsTracker(get()) }

    single<UserRepository> {
        UserRepositoryImpl(
            remoteDatabase = get(),
            localDatabase = get<CoupletDatabase>(),
            authenticator = get(),
            service = get()
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

    single<SafetyWarningRepository> { SafetyWarningRepositoryImpl(get()) }

    single<OnboardingRepository> {
        OnboardingRepositoryImpl(
            context = get(),
            preferences = get()
        )
    }
}