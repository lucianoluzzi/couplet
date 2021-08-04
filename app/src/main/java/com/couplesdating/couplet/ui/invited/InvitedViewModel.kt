package com.couplesdating.couplet.ui.invited

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.userPairing.InvitedEvents
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.useCase.invite.DeleteInviteUseCase
import com.couplesdating.couplet.domain.useCase.invite.InviteExistsUseCase
import com.couplesdating.couplet.domain.useCase.invite.SavePairInviteUseCase
import com.couplesdating.couplet.domain.useCase.pair.FormPairUseCase
import com.couplesdating.couplet.domain.useCase.user.GetCurrentUserUseCase
import kotlinx.coroutines.launch
import java.util.*

class InvitedViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val savePairInviteUseCase: SavePairInviteUseCase,
    private val formPairUseCase: FormPairUseCase,
    private val deleteInviteUseCase: DeleteInviteUseCase,
    private val inviteExistsUseCase: InviteExistsUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val _uiState = MutableLiveData<InvitedUIState>()
    val uiState: LiveData<InvitedUIState> = _uiState

    fun getData(
        inviteId: String,
        inviterId: String,
        inviterDisplayName: String?,
        note: String?,
        timestamp: String?
    ) {
        viewModelScope.launch {
            val inviteExists = inviteExistsUseCase.inviteExists(inviteId)
            val currentUser = getCurrentUserUseCase.getCurrentUser()

            if (currentUser != null && currentUser.userId == inviterId) {
                _uiState.value = InvitedUIState.SameUser
            } else {
                saveReceivedInvite(
                    inviteId = inviteId,
                    inviterId = inviterId,
                    inviterDisplayName = inviterDisplayName,
                    note = note,
                    timestamp = timestamp
                )
                _uiState.value = InvitedUIState.InviteExists(inviteExists)
            }
        }
    }

    private fun saveReceivedInvite(
        inviteId: String,
        inviterId: String,
        inviterDisplayName: String?,
        note: String?,
        timestamp: String?
    ) {
        savePairInviteUseCase.savePairInvite(
            inviteId,
            inviterId,
            inviterDisplayName,
            note,
            false,
            timestamp
        )
    }

    fun onInviteAccepted(
        inviteId: String,
        inviterId: String,
        inviterDisplayName: String?,
        note: String?,
        timestamp: String?
    ) {
        analytics.trackEvent(InvitedEvents.AcceptInviteClicked)
        viewModelScope.launch {
            _uiState.value = InvitedUIState.Loading
            val currentUser = getCurrentUserUseCase.getCurrentUser()
            currentUser?.let {
                val formPairResponse = formPairUseCase.formPair(inviterId)
                if (formPairResponse is Response.Success<*>) {
                    deleteInviteUseCase.deleteInvite(inviteId)
                }
            } ?: run {
                savePairInviteUseCase.savePairInvite(
                    inviteId = inviteId,
                    inviterId = inviterId,
                    inviterDisplayName = inviterDisplayName,
                    note = note,
                    hasAccepted = true,
                    timestamp = timestamp
                )
            }
            _uiState.value = InvitedUIState.AcceptedInvite
        }
    }

    fun onInviteRejected() {
        analytics.trackEvent(InvitedEvents.CloseClicked)
        _uiState.value = InvitedUIState.RejectedInvite
    }
}