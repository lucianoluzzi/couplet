package com.couplesdating.couplet.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import com.couplesdating.couplet.ui.utils.asLiveDataEvent

class RegisterViewModel : ViewModel() {
    private var name = ""
    private var email = ""
    private var password = ""
    private var photo = ""
    private var gender = ""

    private val _emailScreenUIState = MutableLiveData<LiveDataEvent<EmailScreenUIState>>()
    val emailScreenUIState: LiveData<LiveDataEvent<EmailScreenUIState>> = _emailScreenUIState

    fun setEmailAndPassword(
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
            else -> {
                this.email = email
                this.password = password
                _emailScreenUIState.value = EmailScreenUIState.Success.asLiveDataEvent
            }
        }
    }

    fun setNameAndGender(
        name: String,
        gender: String
    ) {
        this.name = name
        this.gender = gender
    }

    fun setPhoto(photo: String) {
        this.photo = photo
    }

    fun register() {

    }
}