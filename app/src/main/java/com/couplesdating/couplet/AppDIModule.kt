package com.couplesdating.couplet

import com.couplesdating.couplet.data.UserRepository
import com.couplesdating.couplet.data.UserRepositoryImpl
import com.couplesdating.couplet.domain.useCase.*
import com.couplesdating.couplet.ui.home.HomeViewModel
import com.couplesdating.couplet.ui.login.emailLogin.LoginViewModel
import com.couplesdating.couplet.ui.login.forgotPassword.ForgotPasswordViewModel
import com.couplesdating.couplet.ui.login.socialLogin.SocialLoginViewModel
import com.couplesdating.couplet.ui.register.emailAndPassword.EmailAndPasswordViewModel
import com.couplesdating.couplet.ui.register.nameAndGender.NameAndGenderViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(Firebase.auth) }

    single<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }

    single<SignInUseCase> { SignInUseCaseImpl(get()) }

    single<RegisterUseCase> { RegisterUseCaseImpl(get()) }

    single<UpdateUserUseCase> { UpdateUserUseCaseImpl(get()) }

    single<ResetPasswordUseCase> { ResetPasswordUseCaseImpl(get()) }

    single<GoogleSignInUseCase> { GoogleSignInUseCaseImpl(get()) }

    single<FacebookSignInUseCase> { FacebookSignInUseCaseImpl(get()) }

    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        SocialLoginViewModel(
            get(),
            get(),
            get()
        )
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        EmailAndPasswordViewModel(get())
    }
    viewModel {
        NameAndGenderViewModel(get())
    }
    viewModel {
        ForgotPasswordViewModel(get())
    }
}