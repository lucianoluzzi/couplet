package com.couplesdating.couplet.analytics.events

private const val SCREEN_NAME = "intimate_onboarding"
sealed class IntimateOnboadingEvents {
    object HellYeahClicked : AnalyticsEvent("${SCREEN_NAME}_hell_yeah_clicked")
}