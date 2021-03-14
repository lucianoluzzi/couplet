package com.couplesdating.couplet.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase

class HomeViewModel(getCurrentUserUseCase: GetCurrentUserUseCase) : ViewModel() {
    val currentUser: LiveData<User?> = liveData {
        emit(getCurrentUserUseCase.getCurrentUser())
    }
}