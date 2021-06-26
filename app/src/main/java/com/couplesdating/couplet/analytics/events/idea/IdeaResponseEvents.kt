package com.couplesdating.couplet.analytics.events.idea

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "idea"

sealed class IdeaResponseEvents {
    data class OnYesClicked(
        val ideaId: String,
        val categoryId: String
    ) : AnalyticsEvent("${SCREEN_NAME}_yes_clicked")

    data class OnNoClicked(
        val ideaId: String,
        val categoryId: String
    ) : AnalyticsEvent("${SCREEN_NAME}_no_clicked")

    data class OnMaybeClicked(
        val ideaId: String,
        val categoryId: String
    ) : AnalyticsEvent("${SCREEN_NAME}_maybe_clicked")
}