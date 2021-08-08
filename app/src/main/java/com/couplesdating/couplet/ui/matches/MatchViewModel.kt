package com.couplesdating.couplet.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.matches.MatchesEvents
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.useCase.match.DeleteAllMatchesUseCase
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import com.couplesdating.couplet.ui.utils.asLiveDataEvent
import kotlinx.coroutines.launch

class MatchViewModel(
    private val deleteAllMatchesUseCase: DeleteAllMatchesUseCase,
    private val analytics: Analytics
) : ViewModel() {

    private val _uiState = MutableLiveData<LiveDataEvent<MatchesUIState>>()
    val uiState: LiveData<LiveDataEvent<MatchesUIState>> = _uiState

    fun onMatchClicked() {
        analytics.trackEvent(MatchesEvents.MatchClick)
    }

    fun onDeleteAllClicked() {
        analytics.trackEvent(MatchesEvents.DeleteAllClick)
    }

    fun onDeleteAllConfirm(user: User) {
        analytics.trackEvent(MatchesEvents.DeleteAllConfirmClick)
        viewModelScope.launch {
            _uiState.value = MatchesUIState.Loading.asLiveDataEvent
            val response = deleteAllMatchesUseCase.deleteAllMatches(user)
            if (response is Response.Completed || response is Response.Success<*>) {
                _uiState.value = MatchesUIState.DeletedMatches.asLiveDataEvent
            } else {
                _uiState.value = MatchesUIState.Error("Ops, something went wrong. Sorry about that!").asLiveDataEvent
            }
        }
    }

    fun onDeleteCancel() {
        analytics.trackEvent(MatchesEvents.DeleteAllCancelClick)
    }
}