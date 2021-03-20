package com.couplesdating.couplet.analytics.events

private const val SCREEN_NAME = "mild_to_wild"
sealed class MildToWildEvents{
    object ContinueClicked : AnalyticsEvent("${SCREEN_NAME}_continue_clicked")
}
