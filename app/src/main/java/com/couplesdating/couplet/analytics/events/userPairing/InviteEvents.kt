package com.couplesdating.couplet.analytics.events.userPairing

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "invite"

sealed class InviteEvents {
    object ShareLinkClicked : AnalyticsEvent("${SCREEN_NAME}_share_link_clicked")
}
