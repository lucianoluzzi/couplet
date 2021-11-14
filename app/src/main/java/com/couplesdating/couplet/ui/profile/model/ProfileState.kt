package com.couplesdating.couplet.ui.profile.model

sealed class ProfileState {
    object Loading : ProfileState()
    data class Settings(val settings: List<SettingsItem>) : ProfileState()
    object Error : ProfileState()
}
