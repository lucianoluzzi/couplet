package com.couplesdating.couplet.ui

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics

class MainViewModel(
    private val analytics: Analytics
) : ViewModel() {

    fun trackScreenShown(screenName: String) {
        analytics.trackScreen(screenName)
    }
}