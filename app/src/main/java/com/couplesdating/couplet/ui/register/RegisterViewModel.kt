package com.couplesdating.couplet.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private var name = ""
    private var email = ""
    private var password = ""
    private var photo = ""
    private var gender = ""

    private val _emailScreenUIState = MutableLiveData<EmailScreenUIState>()
    val emailScreenUIState: LiveData<EmailScreenUIState> = _emailScreenUIState

    fun setEmailAndPassword(
        email: String?,
        password: String?,
        confirmPassword: String?
    ) {
        this.email = email
        this.password = password
    }

    private fun validateEmailAndPasswordFields(
        email: String?,
        password: String?,
        confirmPassword: String?
    ) {
        when {
            email.isNullOrBlank() -> {}
            password.isNullOrBlank() -> {}
            confirmPassword.isNullOrBlank() -> {}
            password != confirmPassword -> {}
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