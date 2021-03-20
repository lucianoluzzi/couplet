package com.couplesdating.couplet.analytics.events

private const val SCREEN_NAME = "lets_start"

sealed class LetsStartEvents {
    object LetsStartClicked : AnalyticsEvent("${SCREEN_NAME}_lets_start_clicked")
}