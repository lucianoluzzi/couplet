package com.couplesdating.couplet.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.domain.useCase.SignInUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _uiStateLiveData = MutableLiveData<LoginUIState>()
    val uiStateLiveData: LiveData<LoginUIState> = _uiStateLiveData

    fun onLoginClicked(email: String?, password: String?) {
        when {
            email.isNullOrBlank() -> {
                _uiStateLiveData.value = LoginUIState.EmailEmpty
            }
            password.isNullOrBlank() -> {
                _uiStateLiveData.value = LoginUIState.PasswordEmpty
            }
            else -> {
                signIn(email, password)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val signedInUser = signInUseCase.signIn(email, password)
            signedInUser?.let {
                _uiStateLiveData.value = LoginUIState.Success
            } ?: run {
                _uiStateLiveData.value = LoginUIState.AuthError
            }
        }
    }
}