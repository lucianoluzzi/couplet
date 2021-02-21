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
        when {
            email.isNullOrBlank() -> _emailScreenUIState.value =
                EmailScreenUIState.EmailEmpty.asLiveDataEvent
            password.isNullOrBlank() -> _emailScreenUIState.value =
                EmailScreenUIState.PasswordEmpty.asLiveDataEvent
            confirmPassword.isNullOrBlank() -> _emailScreenUIState.value =
                EmailScreenUIState.ConfirmPasswordEmpty.asLiveDataEvent
            password != confirmPassword -> _emailScreenUIState.value =
                EmailScreenUIState.PasswordsDoesntMatch.asLiveDataEvent
            else -> register(
                email = email,
                password = password
            )
        }
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
            _emailScreenUIState.value = if (registerResponse is Response.Error) {
                EmailScreenUIState.RegistrationError(
                    registerResponse.errorMessage ?: ""
                ).asLiveDataEvent
            } else {
                EmailScreenUIState.Success.asLiveDataEvent
            }
        }
    }
}