package com.couplesdating.couplet.ui.matches.matchesDetailList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.matches.MatchDetailEvents
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.useCase.match.DeleteMatchUseCase
import com.couplesdating.couplet.domain.useCase.match.GetNewMatchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MatchesDetailListViewModel(
    private val deleteMatchUseCase: DeleteMatchUseCase,
    private val getNewMatchesUseCase: GetNewMatchesUseCase,
    private val analytics: Analytics
) : ViewModel() {

    private val _uiStateFlow: MutableStateFlow<MatchesDetailListUIState> =
        MutableStateFlow(MatchesDetailListUIState.Loading)
    val uiStateFlow: StateFlow<MatchesDetailListUIState> = _uiStateFlow

    init {
        viewModelScope.launch {
            getNewMatchesUseCase.listenToMatches().collect { matches ->
                _uiStateFlow.value = MatchesDetailListUIState.Success(matches)
            }
        }
    }

    fun onDeleteClick() {
        analytics.trackEvent(MatchDetailEvents.OnDeleteClick)
    }

    fun onConfirmDeleteClick(match: Match) {
        analytics.trackEvent(MatchDetailEvents.OnDeleteConfirmClick)
        viewModelScope.launch {
            _uiStateFlow.value = MatchesDetailListUIState.Loading
            when (val deleteResponse = deleteMatchUseCase.deleteMatch(match)) {
                is Response.Success<*>,
                Response.Completed -> _uiStateFlow.value =
                    MatchesDetailListUIState.DeletedMatch(match)
                is Response.Error -> emitErrorResponse(deleteResponse)
            }
        }
    }

    private fun emitErrorResponse(error: Response.Error) {
        val errorMessage =
            error.errorMessage ?: "An error just occurred... Would you mind to try it again?"
        _uiStateFlow.value = MatchesDetailListUIState.Error(errorMessage)
    }

    fun onCancelDeleteClick() {
        analytics.trackEvent(MatchDetailEvents.OnDeleteCancelClick)
    }
}