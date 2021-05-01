package com.couplesdating.couplet.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.domain.useCase.invite.GetInviteUseCase
import com.couplesdating.couplet.domain.useCase.user.GetCurrentUserUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getInviteUseCase: GetInviteUseCase
) : ViewModel() {
    private val _uiData = MutableLiveData<HomeUIData>()
    val uiData: LiveData<HomeUIData> = _uiData

    fun getData() {
        viewModelScope.launch {
            val currentUser = getCurrentUserUseCase.getCurrentUser()
            val acceptedInvite = getInviteUseCase.getInvite()
            _uiData.value = HomeUIData(
                user = currentUser,
                invite = acceptedInvite
            )
        }
    }
}
