package com.couplesdating.couplet.ui.invited

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.domain.useCase.AcceptUserInviteUseCase

class InvitedViewModel(
    private val acceptUserInviteUseCase: AcceptUserInviteUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData<InvitedUIState>()
    val uiState: LiveData<InvitedUIState> = _uiState

    fun onInviteAccepted(inviterId: String) {
        acceptUserInviteUseCase.acceptUserInvite(inviterId)
    }

    fun onInviteRejected() {
        
    }
}