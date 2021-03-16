package com.couplesdating.couplet.analytics.events

open class AnalyticsEvent(
    val key: String,
    val parameters: Map<String, String?>? = null
)