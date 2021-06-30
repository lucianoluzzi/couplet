package com.couplesdating.couplet.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.domain.useCase.invite.GetReceivedInviteUseCase
import com.couplesdating.couplet.domain.useCase.notifications.GetCloudMessagingTokenUseCase
import com.couplesdating.couplet.domain.useCase.user.GetCurrentUserUseCase
import com.couplesdating.couplet.domain.useCase.user.UpdateUserUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getReceivedInviteUseCase: GetReceivedInviteUseCase,
    private val getCloudMessagingTokenUseCase: GetCloudMessagingTokenUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {
    private val _uiData = MutableLiveData<HomeUIData>()
    val uiData: LiveData<HomeUIData> = _uiData

    fun getData() {
        viewModelScope.launch {
            val currentUser = getCurrentUserUseCase.getCurrentUser()
            val acceptedInvite = getReceivedInviteUseCase.getReceivedInvite()
            getCloudMessagingTokenUseCase.getCloudMessageRegistrationToken()?.let { cloudMessageRegistrationToken ->
                if (currentUser?.cloudMessageRegistrationToken != cloudMessageRegistrationToken) {
                    updateUserUseCase.updateCloudMessageRegistrationToken(
                        cloudMessageRegistrationToken
                    )
                }
            }

            _uiData.value = HomeUIData(
                user = currentUser,
                invite = acceptedInvite
            )
        }
    }
}
