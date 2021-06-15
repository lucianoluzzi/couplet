package com.couplesdating.couplet.ui.dashboard.model

import com.couplesdating.couplet.domain.model.Idea

sealed class DashboardRoute {
    data class ToIdeas(val categoryName: String, val ideas: List<Idea>) : DashboardRoute()
    object ToSync : DashboardRoute()
}
