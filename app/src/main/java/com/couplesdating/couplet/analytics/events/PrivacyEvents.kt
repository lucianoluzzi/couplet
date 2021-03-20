package com.couplesdating.couplet.analytics.events

private const val SCREEN_NAME = "privacy"

sealed class PrivacyEvents {
    object ContinueClicked : AnalyticsEvent("${SCREEN_NAME}_continue_clicked")
}