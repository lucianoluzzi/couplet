package com.couplesdating.couplet.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase

class SocialLoginViewModel(
    getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _userLiveData = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> = _userLiveData

    init {
        _userLiveData.value = getCurrentUserUseCase.getCurrentUser()
    }
}