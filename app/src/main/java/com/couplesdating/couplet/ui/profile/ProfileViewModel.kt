package com.couplesdating.couplet.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.domain.useCase.settings.GetSettingsUseCase
import com.couplesdating.couplet.ui.profile.model.SettingsItem

class ProfileViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val _settingsLiveData = MutableLiveData<List<SettingsItem>>()
    val settingsLiveData: LiveData<List<SettingsItem>> = _settingsLiveData

    init {
        _settingsLiveData.value = getSettingsUseCase.getSettings()
    }
}