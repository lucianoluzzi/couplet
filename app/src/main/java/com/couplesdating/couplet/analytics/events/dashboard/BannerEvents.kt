package com.couplesdating.couplet.analytics.events.dashboard

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "dashboard_banner"

sealed class BannerEvents {
    object PendingInviteClicked : AnalyticsEvent("${SCREEN_NAME}_pending_invite_clicked")
    object RegisterPartnerClicked : AnalyticsEvent("${SCREEN_NAME}_register_partner_clicked")
    object NewMatchesClicked : AnalyticsEvent("${SCREEN_NAME}_new_matches_clicked")
    object BecomePremiumClicked : AnalyticsEvent("${SCREEN_NAME}_become_premium_clicked")
    object SentInviteClicked : AnalyticsEvent("${SCREEN_NAME}_sent_invite_clicked")
}