package com.couplesdating.couplet.ui.matches

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.matches.MatchesEvents

class MatchViewModel(
    private val analytics: Analytics
) : ViewModel() {

    fun onMatchClicked() {
        analytics.trackEvent(MatchesEvents.MatchClicked)
    }
}