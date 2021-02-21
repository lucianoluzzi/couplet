package com.couplesdating.couplet.ui.register.emailAndPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.data.UserRepository
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import com.couplesdating.couplet.ui.utils.asLiveDataEvent
import kotlinx.coroutines.launch

class EmailAndPasswordViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _emailScreenUIState = MutableLiveData<LiveDataEvent<EmailScreenUIState>>()
    val emailScreenUIState: LiveData<LiveDataEvent<EmailScreenUIState>> = _emailScreenUIState

    fun validate(
        email: String?,
        password: String?,
        confirmPassword: String?
    ) {
        setLiveDataValue(EmailScreenUIState.Loading)
        when {
            email.isNullOrBlank() -> setLiveDataValue(EmailScreenUIState.EmailEmpty)
            password.isNullOrBlank() -> setLiveDataValue(EmailScreenUIState.PasswordEmpty)
            confirmPassword.isNullOrBlank() -> setLiveDataValue(EmailScreenUIState.ConfirmPasswordEmpty)
            password != confirmPassword -> setLiveDataValue(EmailScreenUIState.PasswordsDoesntMatch)
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
                setLiveDataValue(
                    EmailScreenUIState.RegistrationError(
                        registerResponse.errorMessage ?: ""
                    )
                )
            } else {
                setLiveDataValue(EmailScreenUIState.Success)
            }
        }
    }
}