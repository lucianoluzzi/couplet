package com.couplesdating.couplet.analytics

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class FirebaseAnalyticsProvider {
    fun getFirebaseAnalyticsTracker() = Firebase.analytics
}