package com.couplesdating.couplet.ui.matches.matchesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.matches.MatchesEvents
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.useCase.match.DeleteAllMatchesUseCase
import com.couplesdating.couplet.domain.useCase.match.GetNewMatchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MatchesViewModel(
    private val deleteAllMatchesUseCase: DeleteAllMatchesUseCase,
    private val getNewMatchesUseCase: GetNewMatchesUseCase,
    private val analytics: Analytics
) : ViewModel() {

    private val _uiStateFlow: MutableStateFlow<MatchesUIState> =
        MutableStateFlow(MatchesUIState.Loading)
    val uiStateFlow: StateFlow<MatchesUIState> = _uiStateFlow

    init {
        viewModelScope.launch {
            getNewMatchesUseCase.listenToMatches().collect { matches ->
                _uiStateFlow.value = MatchesUIState.Success(matches)
            }
        }
    }

    fun onMatchClicked() {
        analytics.trackEvent(MatchesEvents.MatchClick)
    }

    fun onDeleteAllClicked() {
        analytics.trackEvent(MatchesEvents.DeleteAllClick)
    }

    fun onDeleteAllConfirm(user: User) {
        analytics.trackEvent(MatchesEvents.DeleteAllConfirmClick)
        viewModelScope.launch {
            _uiStateFlow.value = MatchesUIState.Loading
            val response = deleteAllMatchesUseCase.deleteAllMatches(user)
            if (response is Response.Completed || response is Response.Success<*>) {
                _uiStateFlow.value = MatchesUIState.DeletedMatches
            } else {
                _uiStateFlow.value =
                    MatchesUIState.Error("Ops, something went wrong. Sorry about that!")
            }
        }
    }

    fun onDeleteCancel() {
        analytics.trackEvent(MatchesEvents.DeleteAllCancelClick)
    }
}