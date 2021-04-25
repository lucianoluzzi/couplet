package com.couplesdating.couplet.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.useCase.pair.SetSyncShownUseCase
import com.couplesdating.couplet.domain.useCase.pair.ShouldShowSyncUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val shouldShowSyncUseCase: ShouldShowSyncUseCase,
    private val setSyncShownUseCase: SetSyncShownUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<DashboardUIState>()
    val uiState: LiveData<DashboardUIState> = _uiState

    fun init(currentUser: User?) {
        viewModelScope.launch {
            shouldShowSyncUseCase.invoke().collect {
                if (it && currentUser?.pairedPartner == null) {
                    _uiState.value = DashboardUIState.ShowSync
                }
            }
        }
    }

    fun onSyncShown() {
        setSyncShownUseCase.invoke()
    }
}