package com.couplesdating.couplet.ui.dashboard.model

import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.ui.dashboard.adapter.CategoryUIModel

sealed class DashboardRoute {
    data class ToIdeas(val category: CategoryUIModel, val ideas: List<Idea>) : DashboardRoute()
    data class ToSafetyWarning(val category: CategoryUIModel, val ideas: List<Idea>) :
        DashboardRoute()
    data class MoreOptions(val user: User, val matches: List<Match>) : DashboardRoute()
    object ToSync : DashboardRoute()
}
