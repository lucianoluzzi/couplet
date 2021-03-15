package com.couplesdating.couplet.analytics

interface Analytics {
    fun trackScreen(screenName: String)
    fun trackEvent(key: String, values: Map<String, String?>? = null)
}