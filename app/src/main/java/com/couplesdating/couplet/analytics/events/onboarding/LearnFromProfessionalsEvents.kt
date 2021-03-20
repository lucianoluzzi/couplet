package com.couplesdating.couplet.analytics.events.onboarding

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "learn_from_professionals"
sealed class LearnFromProfessionalsEvents {
    object ContinueClicked : AnalyticsEvent("${SCREEN_NAME}_continue_clicked")
}