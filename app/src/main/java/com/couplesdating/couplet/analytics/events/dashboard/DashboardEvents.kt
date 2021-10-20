package com.couplesdating.couplet.analytics.events.dashboard

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "dashboard"

sealed class DashboardEvents {
    object MoreClicked : AnalyticsEvent("${SCREEN_NAME}_more_clicked")
}
