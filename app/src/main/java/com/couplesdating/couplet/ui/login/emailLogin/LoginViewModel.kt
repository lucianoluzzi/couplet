package com.couplesdating.couplet.ui.login.emailLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.*
import com.couplesdating.couplet.domain.useCase.SignInUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInUseCase: SignInUseCase,
    private val analytics: Analytics
) : ViewModel() {

    private val _uiStateLiveData = MutableLiveData<LoginUIState>()
    val uiStateLiveData: LiveData<LoginUIState> = _uiStateLiveData

    fun onLoginClicked(email: String?, password: String?) {
        analytics.trackEvent(LoginClicked)
        _uiStateLiveData.value = LoginUIState.Loading
        when {
            email.isNullOrBlank() -> {
                analytics.trackEvent(EmailEmptyState)
                _uiStateLiveData.value = LoginUIState.EmailEmpty
            }
            password.isNullOrBlank() -> {
                analytics.trackEvent(PasswordEmptyState)
                _uiStateLiveData.value = LoginUIState.PasswordEmpty
            }
            else -> doSignIn(email, password)
        }
    }

    private fun doSignIn(email: String, password: String) {
        viewModelScope.launch {
            val signedInUser = signInUseCase.signIn(email, password)
            signedInUser?.let {
                analytics.trackEvent(SuccessState)
                _uiStateLiveData.value = LoginUIState.Success
            } ?: run {
                analytics.trackEvent(AuthenticationErrorState)
                _uiStateLiveData.value = LoginUIState.AuthError
            }
        }
    }

    fun onForgotPasswordClicked() {
        analytics.trackEvent(ForgotPasswordClicked)
    }

    fun onEmailInputClicked() {
        analytics.trackEvent(EmailClicked)
    }

    fun onPasswordInputClicked() {
        analytics.trackEvent(PasswordClicked)
    }
}