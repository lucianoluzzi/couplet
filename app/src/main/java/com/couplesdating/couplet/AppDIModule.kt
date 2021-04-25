package com.couplesdating.couplet

import com.couplesdating.couplet.ui.MainViewModel
import com.couplesdating.couplet.ui.dashboard.DashboardFragment
import com.couplesdating.couplet.ui.dashboard.DashboardViewModel
import com.couplesdating.couplet.ui.error.ErrorViewModel
import com.couplesdating.couplet.ui.home.HomeFragment
import com.couplesdating.couplet.ui.home.HomeViewModel
import com.couplesdating.couplet.ui.invite.InvitePartnerViewModel
import com.couplesdating.couplet.ui.invited.InvitedFragment
import com.couplesdating.couplet.ui.invited.InvitedViewModel
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
import org.koin.androidx.fragment.dsl.fragment
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
            getAcceptedInviteUseCase = get(),
            deleteInviteUseCase = get(),
            formPairUseCase = get(),
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
}