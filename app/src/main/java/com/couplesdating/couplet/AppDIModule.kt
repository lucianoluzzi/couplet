package com.couplesdating.couplet

import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.FirebaseAnalyticsProvider
import com.couplesdating.couplet.analytics.FirebaseAnalyticsTracker
import com.couplesdating.couplet.data.*
import com.couplesdating.couplet.domain.useCase.*
import com.couplesdating.couplet.ui.MainViewModel
import com.couplesdating.couplet.ui.error.ErrorViewModel
import com.couplesdating.couplet.ui.home.HomeViewModel
import com.couplesdating.couplet.ui.invite.InvitePartnerViewModel
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
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { DynamicLinkProvider() }

    single { FirestoreProvider().firestoreDatabase }

    single { FirebaseAnalyticsProvider().analytics }

    single { FirebaseAuthProvider().firebaseAuth }

    single<Analytics> { FirebaseAnalyticsTracker(get()) }

    single<UserRepository> { UserRepositoryImpl(get()) }

    single<PairRepository> { PairRepositoryImpl(get()) }

    single<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }

    single<SignInUseCase> { SignInUseCaseImpl(get()) }

    single<RegisterUseCase> { RegisterUseCaseImpl(get()) }

    single<UpdateUserUseCase> { UpdateUserUseCaseImpl(get()) }

    single<ResetPasswordUseCase> { ResetPasswordUseCaseImpl(get()) }

    single<GoogleSignInUseCase> { GoogleSignInUseCaseImpl(get()) }

    single<FacebookSignInUseCase> { FacebookSignInUseCaseImpl(get()) }

    single<GenerateInviteLinkUseCase> { GenerateInviteLinkUseCaseImpl(get()) }

    viewModel {
        MainViewModel(get())
    }
    viewModel {
        ErrorViewModel(get())
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
        NameAndGenderViewModel(
            get(),
            get()
        )
    }
    viewModel {
        ForgotPasswordViewModel(
            get(),
            get()
        )
    }
    viewModel {
        InvitePartnerViewModel(
            get(),
            get(),
            get()
        )
    }
}