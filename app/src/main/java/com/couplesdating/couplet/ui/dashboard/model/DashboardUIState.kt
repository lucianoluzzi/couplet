package com.couplesdating.couplet.ui.dashboard.model

import com.couplesdating.couplet.ui.dashboard.adapter.CategoryUIModel

sealed class DashboardUIState {
    object Loading : DashboardUIState()
    data class Success(
        val categories: List<CategoryUIModel>,
        val banner: Banner? = null
    ) : DashboardUIState()
}
