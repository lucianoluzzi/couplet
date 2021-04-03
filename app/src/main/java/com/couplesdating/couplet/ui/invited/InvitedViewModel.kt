package com.couplesdating.couplet.ui.invited

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.userPairing.InvitedEvents
import com.couplesdating.couplet.domain.useCase.AcceptUserInviteUseCase

class InvitedViewModel(
    private val acceptUserInviteUseCase: AcceptUserInviteUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val _uiState = MutableLiveData<InvitedUIState>()
    val uiState: LiveData<InvitedUIState> = _uiState

    fun onInviteAccepted(inviterId: String) {
        analytics.trackEvent(InvitedEvents.AcceptInviteClicked)
        acceptUserInviteUseCase.acceptUserInvite(inviterId)
        _uiState.value = InvitedUIState.AcceptedInvite
    }

    fun onInviteRejected() {
        analytics.trackEvent(InvitedEvents.RejectInviteClicked)
        _uiState.value = InvitedUIState.RejectedInvite
    }
}