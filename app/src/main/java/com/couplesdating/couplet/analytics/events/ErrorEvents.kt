package com.couplesdating.couplet.analytics.events

private const val SCREEN_NAME = "error"

sealed class ErrorEvents {
    object TryAgainClick : AnalyticsEvent("${SCREEN_NAME}_try_again_click")
}