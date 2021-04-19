package com.couplesdating.couplet.ui.invite

sealed class InvitePartnerUIState {
    data class Success(val inviteUri: String) : InvitePartnerUIState()
    data class Error(val errorMessage: String) : InvitePartnerUIState()
}
