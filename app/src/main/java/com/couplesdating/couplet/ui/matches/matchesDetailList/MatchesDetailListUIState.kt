package com.couplesdating.couplet.ui.matches.matchesDetailList

import com.couplesdating.couplet.domain.model.Match

sealed class MatchesDetailListUIState {
    object Loading : MatchesDetailListUIState()
    data class Success(val matches: List<Match>) : MatchesDetailListUIState()
    data class DeletedMatch(val deletedMatch: Match) : MatchesDetailListUIState()
    data class Error(val errorMessage: String) : MatchesDetailListUIState()
}