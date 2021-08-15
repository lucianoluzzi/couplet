package com.couplesdating.couplet.ui.matches.matchesList

import com.couplesdating.couplet.domain.model.Match

sealed class MatchesUIState {
    object Loading : MatchesUIState()
    object DeletedMatches : MatchesUIState()
    data class Error(val errorMessage: String) : MatchesUIState()
    data class Success(val matches: List<Match>) : MatchesUIState()
}
