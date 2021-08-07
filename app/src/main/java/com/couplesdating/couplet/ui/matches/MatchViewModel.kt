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

    fun onDeleteAllClicked() {
        analytics.trackEvent(MatchesEvents.DeleteAllClick)
    }

    fun onDeleteAll(user: User) {
        analytics.trackEvent(MatchesEvents.DeleteAllConfirmClick)
    }

    fun onDeleteCancel() {
        analytics.trackEvent(MatchesEvents.DeleteAllCancelClick)
    }
}