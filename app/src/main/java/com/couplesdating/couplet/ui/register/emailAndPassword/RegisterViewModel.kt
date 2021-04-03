package com.couplesdating.couplet.ui.register.emailAndPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.login.RegisterEvents
import com.couplesdating.couplet.data.repository.UserRepository
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import com.couplesdating.couplet.ui.utils.asLiveDataEvent
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val analytics: Analytics
) : ViewModel() {

    private val _emailScreenUIState = MutableLiveData<LiveDataEvent<EmailScreenUIState>>()
    val emailScreenUIState: LiveData<LiveDataEvent<EmailScreenUIState>> = _emailScreenUIState

    fun validate(
        email: String?,
        password: String?,
        confirmPassword: String?
    ) {
        analytics.trackEvent(RegisterEvents.RegisterClicked)
        setLiveDataValue(EmailScreenUIState.Loading)
        when {
            email.isNullOrBlank() -> {
                analytics.trackEvent(RegisterEvents.EmailEmpty)
                setLiveDataValue(EmailScreenUIState.EmailEmpty)
            }
            password.isNullOrBlank() -> {
                analytics.trackEvent(RegisterEvents.PasswordEmpty)
                setLiveDataValue(EmailScreenUIState.PasswordEmpty)
            }
            confirmPassword.isNullOrBlank() -> {
                analytics.trackEvent(RegisterEvents.ConfirmPasswordEmpty)
                setLiveDataValue(EmailScreenUIState.ConfirmPasswordEmpty)
            }
            password != confirmPassword -> {
                analytics.trackEvent(RegisterEvents.PasswordsDontMatch)
                setLiveDataValue(EmailScreenUIState.PasswordsDoesntMatch)
            }
            else -> register(
                email = email,
                password = password
            )
        }
    }

    private fun setLiveDataValue(uiState: EmailScreenUIState) {
        _emailScreenUIState.value = uiState.asLiveDataEvent
    }

    private fun register(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val registerResponse = userRepository.register(
                email = email,
                password = password
            )
            if (registerResponse is Response.Error) {
                analytics.trackEvent(
                    RegisterEvents.RegisterError(registerResponse.errorMessage)
                )
                setLiveDataValue(
                    EmailScreenUIState.RegistrationError(
                        registerResponse.errorMessage ?: ""
                    )
                )
            } else {
                analytics.trackEvent(RegisterEvents.RegisterSuccess)
                setLiveDataValue(EmailScreenUIState.Success)
            }
        }
    }

    fun onEmailInputClicked() {
        analytics.trackEvent(RegisterEvents.EmailInputClicked)
    }

    fun onPasswordInputClicked() {
        analytics.trackEvent(RegisterEvents.PasswordInputClicked)
    }

    fun onConfirmPasswordInputclicked() {
        analytics.trackEvent(RegisterEvents.ConfirmPasswordInputClicked)
    }

    fun onPasswordToggleClicked(isPasswordVisible: Boolean) {
        analytics.trackEvent(RegisterEvents.PasswordToggleClicked(isPasswordVisible))
    }

    fun onConfirmPasswordToggleClicked(isConfirmPasswordVisible: Boolean) {
        analytics.trackEvent(RegisterEvents.ConfirmPasswordToggleClicked(isConfirmPasswordVisible))
    }
}