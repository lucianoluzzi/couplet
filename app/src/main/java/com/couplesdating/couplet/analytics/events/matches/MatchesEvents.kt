package com.couplesdating.couplet.analytics.events.matches

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "matches"

sealed class MatchesEvents {
    object MatchClick : AnalyticsEvent("${SCREEN_NAME}_match_click")
    object DeleteAllClick : AnalyticsEvent("${SCREEN_NAME}_delete_all_click")
    object DeleteAllCancelClick : AnalyticsEvent("${SCREEN_NAME}_delete_all_cancel_click")
    object DeleteAllConfirmClick : AnalyticsEvent("${SCREEN_NAME}_delete_all_confirm_click")
}
