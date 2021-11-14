package com.couplesdating.couplet.ui.profile.model

sealed class ProfileIntent {
    data class DeleteAccountClick(val userId: String) : ProfileIntent()
    object EditClick : ProfileIntent()
}
