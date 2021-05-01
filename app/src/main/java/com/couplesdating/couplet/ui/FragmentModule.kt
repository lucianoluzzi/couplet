package com.couplesdating.couplet.ui

import com.couplesdating.couplet.ui.dashboard.DashboardFragment
import com.couplesdating.couplet.ui.dashboard.DashboardViewModel
import com.couplesdating.couplet.ui.home.HomeFragment
import com.couplesdating.couplet.ui.home.HomeViewModel
import com.couplesdating.couplet.ui.invited.InvitedFragment
import com.couplesdating.couplet.ui.invited.InvitedViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val fragmentModule = module {
    fragment {
        val dashboardViewModel = DashboardViewModel(
            shouldShowSyncUseCase = get(),
            setSyncShownUseCase = get(),
            getCategoriesUseCase = get()
        )
        DashboardFragment(dashboardViewModel)
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
            getInviteUseCase = get()
        )
        HomeFragment(homeViewModel)
    }
}