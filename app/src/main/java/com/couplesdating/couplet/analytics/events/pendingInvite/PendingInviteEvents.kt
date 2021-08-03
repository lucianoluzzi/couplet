package com.couplesdating.couplet.analytics.events.pendingInvite

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "pending_invite"
sealed class PendingInviteEvents {
    object AcceptInviteClicked : AnalyticsEvent("${SCREEN_NAME}_accept_invite_clicked")
    object RejectInviteClicked : AnalyticsEvent("${SCREEN_NAME}_reject_invite_clicked")
}
