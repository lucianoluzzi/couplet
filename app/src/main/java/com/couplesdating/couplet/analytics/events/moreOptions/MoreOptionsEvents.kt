package com.couplesdating.couplet.analytics.events.moreOptions

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "more_options"

sealed class MoreOptionsEvents {
    object SeeAllMatchesClicked : AnalyticsEvent("${SCREEN_NAME}_see_all_matches_clicked")
    object MatchClicked : AnalyticsEvent("${SCREEN_NAME}_match_clicked")
    object PartnerClicked : AnalyticsEvent("${SCREEN_NAME}_partner_clicked")
    object ProfileClicked : AnalyticsEvent("${SCREEN_NAME}_profile_clicked")
    object ShareClicked : AnalyticsEvent("${SCREEN_NAME}_share_clicked")
}