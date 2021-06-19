package com.couplesdating.couplet.ui.dashboard.model

import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.ui.dashboard.adapter.CategoryUIModel

sealed class DashboardRoute {
    data class ToIdeas(val category: CategoryUIModel, val ideas: List<Idea>) : DashboardRoute()
    object ToSync : DashboardRoute()
}
