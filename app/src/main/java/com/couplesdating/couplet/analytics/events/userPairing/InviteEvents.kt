package com.couplesdating.couplet.analytics.events.userPairing

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "invite"

sealed class InviteEvents {
    object DisplayNameClicked : AnalyticsEvent("${SCREEN_NAME}_display_name_clicked")
    object NoteClicked : AnalyticsEvent("${SCREEN_NAME}_note_clicked")
    object ShareLinkClicked : AnalyticsEvent("${SCREEN_NAME}_share_link_clicked")
}
