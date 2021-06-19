package com.couplesdating.couplet.ui

import com.couplesdating.couplet.ui.dashboard.DashboardFragment
import com.couplesdating.couplet.ui.home.HomeFragment
import com.couplesdating.couplet.ui.home.HomeViewModel
import com.couplesdating.couplet.ui.idea.IdeaFragment
import com.couplesdating.couplet.ui.idea.IdeaListFragment
import com.couplesdating.couplet.ui.invited.InvitedFragment
import com.couplesdating.couplet.ui.invited.InvitedViewModel
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
            analytics = get()
        )
        InvitedFragment(invitedViewModel)
    }
    fragment {
        val homeViewModel = HomeViewModel(
            getCurrentUserUseCase = get(),
            getReceivedInviteUseCase = get()
        )
        HomeFragment(homeViewModel)
    }
    fragment { IdeaListFragment() }
    fragment { IdeaFragment() }
}