package com.couplesdating.couplet.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase

class MainViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    val userLiveData: LiveData<User?> = liveData {
        emit(getCurrentUserUseCase.getCurrentUser())
    }
}