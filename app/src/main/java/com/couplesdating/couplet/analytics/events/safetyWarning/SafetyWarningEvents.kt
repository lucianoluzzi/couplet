package com.couplesdating.couplet.analytics.events.safetyWarning

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "safety_warning"

sealed class SafetyWarningEvents {
    object GoBackClicked : AnalyticsEvent("${SCREEN_NAME}_go_back_clicked")
    object ContinueClicked : AnalyticsEvent("${SCREEN_NAME}_continue_clicked")
}

