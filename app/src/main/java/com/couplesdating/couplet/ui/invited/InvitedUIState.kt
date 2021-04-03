package com.couplesdating.couplet.ui.invited

sealed class InvitedUIState {
    object AcceptedInvite : InvitedUIState()
    object RejectedInvite : InvitedUIState()
}
