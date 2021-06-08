package com.couplesdating.couplet.analytics.events.dashboard

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "category_banner"

sealed class CategoryEvents {
    data class OnCategoryClicked(val categoryId: String) :
        AnalyticsEvent("${SCREEN_NAME}_category_clicked")
}
