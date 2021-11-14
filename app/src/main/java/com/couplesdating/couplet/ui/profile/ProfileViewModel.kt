package com.couplesdating.couplet.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.domain.useCase.settings.GetSettingsUseCase
import com.couplesdating.couplet.domain.useCase.user.DeleteUserUseCase
import com.couplesdating.couplet.ui.profile.model.ProfileIntent
import com.couplesdating.couplet.ui.profile.model.ProfileState
import kotlinx.coroutines.launch

class ProfileViewModel(
    getSettingsUseCase: GetSettingsUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val _profileStateLiveData = MutableLiveData<ProfileState>()
    val profileStateLiveData: LiveData<ProfileState> = _profileStateLiveData

    init {
        _profileStateLiveData.value = ProfileState.Loading
        _profileStateLiveData.value = ProfileState.Settings(getSettingsUseCase.getSettings())
    }

    fun onIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.EditClick -> {}
            is ProfileIntent.DeleteAccountClick -> deleteAccount(intent)
        }
    }

    private fun deleteAccount(deleteAccountClick: ProfileIntent.DeleteAccountClick) {
        viewModelScope.launch {
            val response = deleteUserUseCase.deleteUser(deleteAccountClick.userId)

        }
    }
}