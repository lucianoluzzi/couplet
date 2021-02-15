package com.couplesdating.couplet.ui.register.emailAndPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import com.couplesdating.couplet.ui.utils.asLiveDataEvent

class EmailAndPasswordViewModel : ViewModel() {
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
            else -> _emailScreenUIState.value = EmailScreenUIState.Success.asLiveDataEvent
        }
    }
}