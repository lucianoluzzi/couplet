package com.couplesdating.couplet.ui.pendingInvite

sealed class PendingInviteUIState {
    object Loading : PendingInviteUIState()
    object Accepted : PendingInviteUIState()
    object Reject : PendingInviteUIState()
    data class Error(val errorMessage: String) : PendingInviteUIState()
}
