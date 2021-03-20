package com.couplesdating.couplet

import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.FirebaseAnalyticsProvider
import com.couplesdating.couplet.analytics.FirebaseAnalyticsTracker
import com.couplesdating.couplet.data.UserRepository
import com.couplesdating.couplet.data.UserRepositoryImpl
import com.couplesdating.couplet.domain.useCase.*
import com.couplesdating.couplet.ui.MainViewModel
import com.couplesdating.couplet.ui.home.HomeViewModel
import com.couplesdating.couplet.ui.login.emailLogin.LoginViewModel
import com.couplesdating.couplet.ui.login.forgotPassword.ForgotPasswordViewModel
import com.couplesdating.couplet.ui.login.socialLogin.SocialLoginViewModel
import com.couplesdating.couplet.ui.onboarding.intimate.IntimateOnboardingViewModel
import com.couplesdating.couplet.ui.onboarding.learnFromProfessionals.LearnFromProfessionalsViewModel
import com.couplesdating.couplet.ui.onboarding.letsStart.LetsStartViewModel
import com.couplesdating.couplet.ui.onboarding.mildToWild.MildToWildViewModel
import com.couplesdating.couplet.ui.onboarding.privacy.PrivacyViewModel
import com.couplesdating.couplet.ui.register.emailAndPassword.RegisterViewModel
import com.couplesdating.couplet.ui.register.nameAndGender.NameAndGenderViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Analytics> {
        FirebaseAnalyticsTracker(
            FirebaseAnalyticsProvider().getFirebaseAnalyticsTracker()
        )
    }

    single<UserRepository> { UserRepositoryImpl(Firebase.auth) }

    single<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }

    single<SignInUseCase> { SignInUseCaseImpl(get()) }

    single<RegisterUseCase> { RegisterUseCaseImpl(get()) }

    single<UpdateUserUseCase> { UpdateUserUseCaseImpl(get()) }

    single<ResetPasswordUseCase> { ResetPasswordUseCaseImpl(get()) }

    single<GoogleSignInUseCase> { GoogleSignInUseCaseImpl(get()) }

    single<FacebookSignInUseCase> { FacebookSignInUseCaseImpl(get()) }

    viewModel {
        MainViewModel(get())
    }
    viewModel {
        PrivacyViewModel(get())
    }
    viewModel {
        LetsStartViewModel(get())
    }
    viewModel {
        IntimateOnboardingViewModel(get())
    }
    viewModel {
        LearnFromProfessionalsViewModel(get())
    }
    viewModel {
        MildToWildViewModel(get())
    }
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        SocialLoginViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel {
        LoginViewModel(
            get(),
            get()
        )
    }
    viewModel {
        RegisterViewModel(
            get(),
            get()
        )
    }
    viewModel {
        NameAndGenderViewModel(get())
    }
    viewModel {
        ForgotPasswordViewModel(
            get(),
            get()
        )
    }
}