package com.couplesdating.couplet.ui.invited

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.userPairing.InvitedEvents
import com.couplesdating.couplet.domain.useCase.invite.AcceptUserInviteUseCase
import com.couplesdating.couplet.domain.useCase.invite.InviteExistsUseCase
import com.couplesdating.couplet.domain.useCase.user.GetCurrentUserUseCase
import kotlinx.coroutines.launch

class InvitedViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val acceptUserInviteUseCase: AcceptUserInviteUseCase,
    private val inviteExistsUseCase: InviteExistsUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val _uiState = MutableLiveData<InvitedUIState>()
    val uiState: LiveData<InvitedUIState> = _uiState

    fun getData(inviteId: String) {
        viewModelScope.launch {
            val inviteExists = inviteExistsUseCase.inviteExists(inviteId)
            val currentUser = getCurrentUserUseCase.getCurrentUser()

            if (currentUser != null) {
                _uiState.value = InvitedUIState.SameUser
            } else {
                _uiState.value = InvitedUIState.InviteExists(inviteExists)
            }
        }
    }

    fun onInviteAccepted(
        inviteId: String,
        inviterId: String
    ) {
        analytics.trackEvent(InvitedEvents.AcceptInviteClicked)
        acceptUserInviteUseCase.acceptUserInvite(
            inviteId,
            inviterId
        )
        _uiState.value = InvitedUIState.AcceptedInvite
    }

    fun onInviteRejected() {
        analytics.trackEvent(InvitedEvents.CloseClicked)
        _uiState.value = InvitedUIState.RejectedInvite
    }
}