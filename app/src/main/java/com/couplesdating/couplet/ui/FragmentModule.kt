package com.couplesdating.couplet.ui

import com.couplesdating.couplet.ui.acceptedInvite.AcceptedInviteFragment
import com.couplesdating.couplet.ui.dashboard.DashboardFragment
import com.couplesdating.couplet.ui.home.HomeFragment
import com.couplesdating.couplet.ui.home.HomeViewModel
import com.couplesdating.couplet.ui.idea.IdeaFragment
import com.couplesdating.couplet.ui.ideaList.IdeaListFragment
import com.couplesdating.couplet.ui.info.InfoFragment
import com.couplesdating.couplet.ui.invited.InvitedFragment
import com.couplesdating.couplet.ui.invited.InvitedViewModel
import com.couplesdating.couplet.ui.matches.MatchesFragment
import com.couplesdating.couplet.ui.pendingInvite.PendingInviteFragment
import com.couplesdating.couplet.ui.rejectedInvite.RejectedInviteFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val fragmentModule = module {
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
            updateUserUseCase = get()
        )
        HomeFragment(homeViewModel)
    }
    fragment { IdeaListFragment(get()) }
    fragment { IdeaFragment() }
    fragment { InfoFragment() }
    fragment { PendingInviteFragment(get()) }
    fragment { AcceptedInviteFragment() }
    fragment { RejectedInviteFragment() }
    fragment {
        MatchesFragment(
            viewModel = get()
        )
    }
}