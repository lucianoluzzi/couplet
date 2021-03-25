package com.couplesdating.couplet.ui.error

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.ErrorEvents

class ErrorViewModel(
    private val analytics: Analytics
) : ViewModel() {

    fun onTryAgainClicked() {
        analytics.trackEvent(ErrorEvents.TryAgainClick)
    }
}