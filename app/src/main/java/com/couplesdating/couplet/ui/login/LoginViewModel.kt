package com.couplesdating.couplet.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _uiStateLiveData = MutableLiveData<LoginUIState>()
    val uiStateLiveData: LiveData<LoginUIState> = _uiStateLiveData

    fun onLoginClicked(email: String?, password: String?) {
        if (email.isNullOrBlank()) {
            _uiStateLiveData.value = LoginUIState.EmailEmpty
        } else if (password.isNullOrBlank()) {
            _uiStateLiveData.value = LoginUIState.PasswordEmpty
        }
    }
}