package com.couplesdating.couplet.ui.invited

sealed class InvitedUIState {
    object Loading : InvitedUIState()
    object AcceptedInvite : InvitedUIState()
    object RejectedInvite : InvitedUIState()
    object SameUser : InvitedUIState()
    data class InviteExists(val exists: Boolean) : InvitedUIState()
}
