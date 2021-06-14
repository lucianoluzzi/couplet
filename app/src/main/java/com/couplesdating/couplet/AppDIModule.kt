package com.couplesdating.couplet

import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.ui.MainViewModel
import com.couplesdating.couplet.ui.dashboard.DashboardViewModel
import com.couplesdating.couplet.ui.error.ErrorViewModel
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
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

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
        SocialLoginViewModel(
            getCurrentUserUseCase = get(),
            googleSignInUseCase = get(),
            facebookSignInUseCase = get(),
            getReceivedInviteUseCase = get(),
            deleteInviteUseCase = get(),
            formPairUseCase = get(),
            addInviteeIdUseCase = get(),
            analytics = get()
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
            get(),
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
            get(),
            get()
        )
    }
    viewModel { (user: User) ->
        DashboardViewModel(
            shouldShowSyncUseCase = get(),
            setSyncShownUseCase = get(),
            getCategoriesUseCase = get(),
            getReceivedInviteUseCase = get(),
            getSentPairInviteUseCase = get(),
            getNewMatchesUseCase = get(),
            analytics = get(),
            currentUser = user
        )
    }
}