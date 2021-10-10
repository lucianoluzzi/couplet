package com.couplesdating.couplet.analytics.events.userPairing

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "sent_invite"

sealed class SentInviteEvents {
    object DeleteInviteClicked : AnalyticsEvent("${SCREEN_NAME}_delete_invite_clicked")
    object DeleteConfirmClicked : AnalyticsEvent("${SCREEN_NAME}_delete_invite_confirm_clicked")
    object DeleteCancelClicked : AnalyticsEvent("${SCREEN_NAME}_delete_invite_cancel_clicked")
}
