package com.couplesdating.couplet.ui

import com.couplesdating.couplet.ui.acceptedInvite.AcceptedInviteFragment
import com.couplesdating.couplet.ui.acceptedInvite.PartnerAcceptedInviteFragment
import com.couplesdating.couplet.ui.dashboard.DashboardFragment
import com.couplesdating.couplet.ui.home.HomeFragment
import com.couplesdating.couplet.ui.home.HomeViewModel
import com.couplesdating.couplet.ui.idea.IdeaFragment
import com.couplesdating.couplet.ui.ideaList.IdeaListFragment
import com.couplesdating.couplet.ui.info.InfoFragment
import com.couplesdating.couplet.ui.invite.RegisterPartnerFragment
import com.couplesdating.couplet.ui.invited.InvitedFragment
import com.couplesdating.couplet.ui.invited.InvitedViewModel
import com.couplesdating.couplet.ui.matches.matchesDetailList.MatchesDetailListFragment
import com.couplesdating.couplet.ui.matches.matchesList.MatchesFragment
import com.couplesdating.couplet.ui.more.MoreFragment
import com.couplesdating.couplet.ui.onboarding.privacy.PrivacyOnboardingFragment
import com.couplesdating.couplet.ui.pendingInvite.PendingInviteFragment
import com.couplesdating.couplet.ui.rejectedInvite.RejectedInviteFragment
import com.couplesdating.couplet.ui.safetyWarning.SafetyWarningFragment
import com.couplesdating.couplet.ui.sentInvite.SentInviteFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val fragmentModule = module {
    fragment {
        PrivacyOnboardingFragment(
            viewModel = get()
        )
    }
    fragment {
        RegisterPartnerFragment(get())
    }
    fragment {
        DashboardFragment()
    }
    fragment {
        val invitedViewModel = InvitedViewModel(
            getCurrentUserUseCase = get(),
            savePairInviteUseCase = get(),
            inviteExistsUseCase = get(),
            formPairUseCase = get(),
            deleteInviteUseCase = get(),
            analytics = get()
        )
        InvitedFragment(invitedViewModel)
    }
    fragment {
        val homeViewModel = HomeViewModel(
            getCurrentUserUseCase = get(),
            getReceivedInviteUseCase = get(),
            getCloudMessagingTokenUseCase = get(),
            updateUserUseCase = get(),
            getHasSeenOnboardingUseCase = get()
        )
        HomeFragment(homeViewModel)
    }
    fragment { IdeaListFragment(get()) }
    fragment { IdeaFragment() }
    fragment { InfoFragment() }
    fragment { PendingInviteFragment(get()) }
    fragment { SentInviteFragment(get()) }
    fragment { AcceptedInviteFragment() }
    fragment { RejectedInviteFragment() }
    fragment {
        MatchesFragment(
            viewModel = get()
        )
    }
    fragment {
        MatchesDetailListFragment(
            viewModel = get()
        )
    }
    fragment {
        SafetyWarningFragment(
            viewModel = get()
        )
    }
    fragment {
        PartnerAcceptedInviteFragment()
    }
    fragment {
        MoreFragment(
            viewModel = get()
        )
    }
}