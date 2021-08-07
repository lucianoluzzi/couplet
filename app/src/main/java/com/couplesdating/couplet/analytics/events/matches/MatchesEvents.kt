package com.couplesdating.couplet.analytics.events.matches

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "matches"

sealed class MatchesEvents {
    object MatchClicked : AnalyticsEvent("${SCREEN_NAME}_match_clicked")
}
