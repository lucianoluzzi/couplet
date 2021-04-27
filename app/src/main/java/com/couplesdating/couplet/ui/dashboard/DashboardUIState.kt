package com.couplesdating.couplet.ui.dashboard

import com.couplesdating.couplet.ui.dashboard.adapter.CategoryUIModel

sealed class DashboardUIState {
    object Loading : DashboardUIState()
    data class Success(val categories: List<CategoryUIModel>) : DashboardUIState()
}
