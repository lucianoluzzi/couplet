package com.couplesdating.couplet

import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.ui.MainViewModel
import com.couplesdating.couplet.ui.dashboard.DashboardViewModel
import com.couplesdating.couplet.ui.error.ErrorViewModel
import com.couplesdating.couplet.ui.idea.IdeaViewModel
import com.couplesdating.couplet.ui.ideaList.IdeaListViewModel
import com.couplesdating.couplet.ui.invite.InvitePartnerViewModel
import com.couplesdating.couplet.ui.login.emailLogin.LoginViewModel
import com.couplesdating.couplet.ui.login.forgotPassword.ForgotPasswordViewModel
import com.couplesdating.couplet.ui.login.socialLogin.SocialLoginViewModel
import com.couplesdating.couplet.ui.matches.matchesDetailList.MatchesDetailListViewModel
import com.couplesdating.couplet.ui.matches.matchesList.MatchesViewModel
import com.couplesdating.couplet.ui.onboarding.intimate.IntimateOnboardingViewModel
import com.couplesdating.couplet.ui.onboarding.learnFromProfessionals.LearnFromProfessionalsViewModel
import com.couplesdating.couplet.ui.onboarding.letsStart.LetsStartViewModel
import com.couplesdating.couplet.ui.onboarding.mildToWild.MildToWildViewModel
import com.couplesdating.couplet.ui.onboarding.privacy.PrivacyViewModel
import com.couplesdating.couplet.ui.pendingInvite.PendingInviteViewModel
import com.couplesdating.couplet.ui.register.emailAndPassword.RegisterViewModel
import com.couplesdating.couplet.ui.register.nameAndGender.NameAndGenderViewModel
import com.couplesdating.couplet.ui.safetyWarning.SafetyWarningViewModel
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
        PrivacyViewModel(
            setOnboardingShownUseCase = get(),
            analytics = get()
        )
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
            signInUseCase = get(),
            getReceivedInviteUseCase = get(),
            deleteInviteUseCase = get(),
            formPairUseCase = get(),
            addInviteeIdUseCase = get(),
            analytics = get()
        )
    }
    viewModel {
        RegisterViewModel(
            userRepository = get(),
            getReceivedInviteUseCase = get(),
            formPairUseCase = get(),
            addInviteeIdUseCase = get(),
            deleteInviteUseCase = get(),
            analytics = get()
        )
    }
    viewModel {
        NameAndGenderViewModel(
            updateUserUseCase = get(),
            analytics = get()
        )
    }
    viewModel {
        ForgotPasswordViewModel(
            resetPasswordUseCase = get(),
            analitycs = get()
        )
    }
    viewModel {
        InvitePartnerViewModel(
            getCurrentUserUseCase = get(),
            generateInviteLinkUseCase = get(),
            createInviteUseCase = get(),
            analytics = get()
        )
    }
    viewModel { (user: User) ->
        DashboardViewModel(
            shouldShowSyncUseCase = get(),
            setSyncShownUseCase = get(),
            getCategoriesUseCase = get(),
            refreshCategoriesUseCase = get(),
            getReceivedInviteUseCase = get(),
            getSentPairInviteUseCase = get(),
            getNewMatchesUseCase = get(),
            getPartnerUseCase = get(),
            getHasSeenSafetyWarningUseCase = get(),
            analytics = get(),
            currentUser = user
        )
    }
    viewModel {
        IdeaViewModel(get())
    }
    viewModel {
        IdeaListViewModel(
            sendIdeaResponseUseCase = get(),
            decorateIdeaUseCase = get(),
            removeIdeaUseCase = get(),
            analytics = get()
        )
    }
    viewModel {
        PendingInviteViewModel(
            formPairUseCase = get(),
            deleteInviteUseCase = get(),
            analytics = get()
        )
    }
    viewModel {
        MatchesViewModel(
            deleteAllMatchesUseCase = get(),
            getNewMatchesUseCase = get(),
            analytics = get()
        )
    }
    viewModel {
        MatchesDetailListViewModel(
            deleteMatchUseCase = get(),
            getNewMatchesUseCase = get(),
            analytics = get()
        )
    }
    viewModel {
        SafetyWarningViewModel(
            setHasSeenSafetyWarningUseCase = get(),
            analytics = get()
        )
    }
}