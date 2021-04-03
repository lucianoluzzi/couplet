package com.couplesdating.couplet.analytics.events.userPairing

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "invited"
sealed class InvitedEvents {
    object AcceptInviteClicked : AnalyticsEvent("${SCREEN_NAME}_accept_invite_clicked")
    object RejectInviteClicked : AnalyticsEvent("${SCREEN_NAME}_reject_invite_clicked")
}
