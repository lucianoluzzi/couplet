package com.couplesdating.couplet.ui.invited

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.userPairing.InvitedEvents
import com.couplesdating.couplet.domain.useCase.invite.InviteExistsUseCase
import com.couplesdating.couplet.domain.useCase.invite.SavePairInviteUseCase
import com.couplesdating.couplet.domain.useCase.user.GetCurrentUserUseCase
import kotlinx.coroutines.launch

class InvitedViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val savePairInviteUseCase: SavePairInviteUseCase,
    private val inviteExistsUseCase: InviteExistsUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val _uiState = MutableLiveData<InvitedUIState>()
    val uiState: LiveData<InvitedUIState> = _uiState

    fun getData(
        inviteId: String,
        inviterId: String,
        inviterDisplayName: String?,
        note: String?
    ) {
        saveReceivedInvite(
            inviteId = inviteId,
            inviterId = inviterId,
            inviterDisplayName = inviterDisplayName,
            note = note
        )
        viewModelScope.launch {
            val inviteExists = inviteExistsUseCase.inviteExists(inviteId)
            val currentUser = getCurrentUserUseCase.getCurrentUser()

            if (currentUser != null && currentUser.userId == inviterId) {
                _uiState.value = InvitedUIState.SameUser
            } else {
                _uiState.value = InvitedUIState.InviteExists(inviteExists)
            }
        }
    }

    private fun saveReceivedInvite(
        inviteId: String,
        inviterId: String,
        inviterDisplayName: String?,
        note: String?
    ) {
        savePairInviteUseCase.savePairInvite(
            inviteId,
            inviterId,
            inviterDisplayName,
            note,
            false
        )
    }

    fun onInviteAccepted(
        inviteId: String,
        inviterId: String,
        inviterDisplayName: String?,
        note: String?
    ) {
        analytics.trackEvent(InvitedEvents.AcceptInviteClicked)
        savePairInviteUseCase.savePairInvite(
            inviteId,
            inviterId,
            inviterDisplayName,
            note,
            true
        )
        _uiState.value = InvitedUIState.AcceptedInvite
    }

    fun onInviteRejected() {
        analytics.trackEvent(InvitedEvents.CloseClicked)
        _uiState.value = InvitedUIState.RejectedInvite
    }
}