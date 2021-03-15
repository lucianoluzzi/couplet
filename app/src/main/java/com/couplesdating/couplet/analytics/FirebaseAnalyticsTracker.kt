package com.couplesdating.couplet.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticsTracker(
    private val firebaseAnalytics: FirebaseAnalytics
) : Analytics {

    override fun trackScreen(screenName: String) {
        val screenMap = mapOf(
            FirebaseAnalytics.Param.SCREEN_NAME to screenName,
            FirebaseAnalytics.Param.SCREEN_CLASS to screenName
        )
        trackEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screenMap)
    }

    override fun trackEvent(key: String, values: Map<String, String?>?) {
        val bundle: Bundle? = values?.let {
            val bundle = Bundle()
            for (entry: Map.Entry<String, String?> in it.entries) {
                bundle.putString(entry.key, entry.value)
            }
            bundle
        }
        firebaseAnalytics.logEvent(key, bundle)
    }
}