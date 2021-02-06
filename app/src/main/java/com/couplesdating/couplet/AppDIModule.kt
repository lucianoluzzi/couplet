package com.couplesdating.couplet

import com.couplesdating.couplet.data.UserRepository
import com.couplesdating.couplet.data.UserRepositoryImpl
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCaseImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(Firebase.auth) }

    single<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }
}