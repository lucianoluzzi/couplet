package com.couplesdating.couplet.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase

class LoginViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    val userLiveData: LiveData<User?> = liveData {
        emit(getCurrentUserUseCase.getCurrentUser())
    }


}