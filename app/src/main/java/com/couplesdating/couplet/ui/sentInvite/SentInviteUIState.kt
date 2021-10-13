package com.couplesdating.couplet.ui.sentInvite

sealed class SentInviteUIState {
    object Loading : SentInviteUIState()
    object Error : SentInviteUIState()
    object Success : SentInviteUIState()
}
