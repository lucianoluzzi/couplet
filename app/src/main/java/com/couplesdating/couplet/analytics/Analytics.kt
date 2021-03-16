package com.couplesdating.couplet.analytics

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

interface Analytics {
    fun trackScreen(screenName: String)
    fun trackEvent(key: String, values: Map<String, String?>? = null)
    fun trackEvent(event: AnalyticsEvent)
}