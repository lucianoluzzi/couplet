package com.couplesdating.couplet.ui.more

import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.useCase.match.GetRecentMatchesUseCase

class MoreOptionsViewModel(
    private val getRecentMatchesUseCase: GetRecentMatchesUseCase
) : ViewModel() {

    fun getRecentMatches(matches: List<Match>): List<RecentMatch> {
        val recentMatches = getRecentMatchesUseCase.getRecentMatches(matches)
        return recentMatches.mapIndexed { index, match ->
            RecentMatch(
                number = index,
                description = match.idea.description
            )
        }
    }
}