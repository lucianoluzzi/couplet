package com.couplesdating.couplet.ui.matches

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.matches.MatchesEvents
import com.couplesdating.couplet.domain.model.User

class MatchViewModel(
    private val analytics: Analytics
) : ViewModel() {

    fun onMatchClicked() {
        analytics.trackEvent(MatchesEvents.MatchClick)
    }

    fun onDeleteAllClicked(user: User) {
        analytics.trackEvent(MatchesEvents.DeleteAllClick)
    }
}