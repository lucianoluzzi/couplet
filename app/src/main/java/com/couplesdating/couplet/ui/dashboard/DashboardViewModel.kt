package com.couplesdating.couplet.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.domain.useCase.SetSyncShownUseCase
import com.couplesdating.couplet.domain.useCase.ShouldShowSyncUseCase

class DashboardViewModel(
    private val shouldShowSyncUseCase: ShouldShowSyncUseCase,
    private val setSyncShownUseCase: SetSyncShownUseCase
) : ViewModel() {

    private val _shouldShowSyncScreen = MutableLiveData<Boolean>()
    val shouldShowSyncScreen: LiveData<Boolean> = _shouldShowSyncScreen

    init {
        _shouldShowSyncScreen.value = shouldShowSyncUseCase.invoke()
    }

    fun onSyncShown() {
        setSyncShownUseCase.invoke()
    }
}