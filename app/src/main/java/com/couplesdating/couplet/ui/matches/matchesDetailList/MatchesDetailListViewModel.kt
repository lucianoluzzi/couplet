package com.couplesdating.couplet.ui.matches.matchesDetailList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.matches.MatchDetailEvents
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.useCase.match.DeleteMatchUseCase
import kotlinx.coroutines.launch

class MatchesDetailListViewModel(
    private val deleteMatchUseCase: DeleteMatchUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val _uiState = MutableLiveData<MatchesDetailListUIState>()
    val uiState: LiveData<MatchesDetailListUIState> = _uiState

    fun onDeleteClick() {
        analytics.trackEvent(MatchDetailEvents.OnDeleteClick)
    }

    fun onConfirmDeleteClick(match: Match) {
        analytics.trackEvent(MatchDetailEvents.OnDeleteConfirmClick)
        viewModelScope.launch {
            _uiState.value = MatchesDetailListUIState.Loading
            when (val deleteResponse = deleteMatchUseCase.deleteMatch(match)) {
                is Response.Success<*>,
                Response.Completed -> _uiState.value = MatchesDetailListUIState.Success(match)
                is Response.Error -> emitErrorResponse(deleteResponse)
            }
        }
    }

    private fun emitErrorResponse(error: Response.Error) {
        val errorMessage =
            error.errorMessage ?: "An error just occurred... Would you mind to try it again?"
        _uiState.value = MatchesDetailListUIState.Error(errorMessage)
    }

    fun onCancelDeleteClick() {
        analytics.trackEvent(MatchDetailEvents.OnDeleteCancelClick)
    }
}