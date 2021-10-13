package com.couplesdating.couplet.ui.sentInvite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.userPairing.SentInviteEvents
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.useCase.invite.DeleteInviteUseCase
import kotlinx.coroutines.launch

class SentInviteViewModel(
    private val deleteInviteUseCase: DeleteInviteUseCase,
    private val analytics: Analytics
) : ViewModel() {

    private val _deleteInviteLiveData = MutableLiveData<SentInviteUIState>()
    val deleteInviteLiveData: LiveData<SentInviteUIState> = _deleteInviteLiveData

    fun onDeleteClick() {
        analytics.trackEvent(SentInviteEvents.DeleteInviteClicked)
    }

    fun onDeleteConfirm(invite: InviteModel) {
        analytics.trackEvent(SentInviteEvents.DeleteConfirmClicked)
        viewModelScope.launch {
            _deleteInviteLiveData.value = SentInviteUIState.Loading
            try {
                deleteInviteUseCase.deleteInvite(invite)
                _deleteInviteLiveData.value = SentInviteUIState.Success
            } catch (exception: Exception) {
                _deleteInviteLiveData.value = SentInviteUIState.Error
            }
        }
    }

    fun onDeleteCancel() {
        analytics.trackEvent(SentInviteEvents.DeleteCancelClicked)
    }
}