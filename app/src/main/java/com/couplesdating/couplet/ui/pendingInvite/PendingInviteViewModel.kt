package com.couplesdating.couplet.ui.pendingInvite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.userPairing.PendingInviteEvents
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.useCase.invite.DeleteInviteUseCase
import com.couplesdating.couplet.domain.useCase.pair.FormPairUseCase
import kotlinx.coroutines.launch

class PendingInviteViewModel(
    private val formPairUseCase: FormPairUseCase,
    private val deleteInviteUseCase: DeleteInviteUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val _uiState = MutableLiveData<PendingInviteUIState>()
    val uiState: LiveData<PendingInviteUIState> = _uiState

    fun onAcceptInvite(inviteModel: InviteModel) {
        analytics.trackEvent(PendingInviteEvents.AcceptInviteClicked)
        viewModelScope.launch {
            val errorMessage = "Damn, I'm having some trouble here. Mind to try it again?"
            _uiState.value = PendingInviteUIState.Loading
            try {
                val formPairResponse = formPairUseCase.formPair(inviteModel.inviterId)
                if (formPairResponse is Response.Success<*>) {
                    deleteInviteUseCase.deleteInvite(inviteModel)
                    _uiState.value = PendingInviteUIState.Accepted
                } else {
                    _uiState.value = PendingInviteUIState.Error(errorMessage)
                }
            } catch (exception: Exception) {
                _uiState.value = PendingInviteUIState.Error(errorMessage)
            }
        }
    }

    fun onRejectInvite(inviteModel: InviteModel) {
        analytics.trackEvent(PendingInviteEvents.RejectInviteClicked)
        viewModelScope.launch {
            val errorMessage = "Damn, I'm having some trouble here. Mind to try it again?"
            _uiState.value = PendingInviteUIState.Loading
            try {
                deleteInviteUseCase.deleteInvite(inviteModel)
                _uiState.value = PendingInviteUIState.Reject
            } catch (exception: Exception) {
                _uiState.value = PendingInviteUIState.Error(errorMessage)
            }
        }
    }
}