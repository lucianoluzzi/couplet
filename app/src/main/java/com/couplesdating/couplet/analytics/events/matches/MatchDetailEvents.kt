package com.couplesdating.couplet.analytics.events.matches

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "match_details"

sealed class MatchDetailEvents {
    object OnDeleteClick : AnalyticsEvent("${SCREEN_NAME}_delete_click")
    object OnDeleteConfirmClick : AnalyticsEvent("${SCREEN_NAME}_delete_confirm_click")
    object OnDeleteCancelClick : AnalyticsEvent("${SCREEN_NAME}_delete_cancel_click")
}
