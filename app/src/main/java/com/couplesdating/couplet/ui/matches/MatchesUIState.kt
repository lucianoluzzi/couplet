package com.couplesdating.couplet.ui.matches

sealed class MatchesUIState {
    object Loading : MatchesUIState()
    object DeletedMatches : MatchesUIState()
    data class Error(val errorMessage: String) : MatchesUIState()
}
