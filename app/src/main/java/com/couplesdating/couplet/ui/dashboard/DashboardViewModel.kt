package com.couplesdating.couplet.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.domain.useCase.SetSyncShownUseCase
import com.couplesdating.couplet.domain.useCase.ShouldShowSyncUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val shouldShowSyncUseCase: ShouldShowSyncUseCase,
    private val setSyncShownUseCase: SetSyncShownUseCase
) : ViewModel() {

    private val _shouldShowSyncScreen = MutableLiveData<Boolean>()
    val shouldShowSyncScreen: LiveData<Boolean> = _shouldShowSyncScreen

    init {
        viewModelScope.launch {
            shouldShowSyncUseCase.invoke().collect {
                _shouldShowSyncScreen.value = it
            }
        }

    }

    fun onSyncShown() {
        setSyncShownUseCase.invoke()
    }
}