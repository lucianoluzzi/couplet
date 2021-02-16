package com.couplesdating.couplet

import com.couplesdating.couplet.data.UserRepository
import com.couplesdating.couplet.data.UserRepositoryImpl
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCaseImpl
import com.couplesdating.couplet.domain.useCase.SignInUseCase
import com.couplesdating.couplet.domain.useCase.SignInUseCaseImpl
import com.couplesdating.couplet.ui.login.LoginViewModel
import com.couplesdating.couplet.ui.register.RegisterViewModel
import com.couplesdating.couplet.ui.register.emailAndPassword.EmailAndPasswordViewModel
import com.couplesdating.couplet.ui.register.nameAndGender.NameAndGenderViewModel
import com.couplesdating.couplet.ui.viewModel.SocialLoginViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(Firebase.auth) }

    single<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }

    single<SignInUseCase> { SignInUseCaseImpl(get()) }

    viewModel {
        SocialLoginViewModel(get())
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        EmailAndPasswordViewModel()
    }
    viewModel {
        NameAndGenderViewModel()
    }
    viewModel {
        RegisterViewModel()
    }
}