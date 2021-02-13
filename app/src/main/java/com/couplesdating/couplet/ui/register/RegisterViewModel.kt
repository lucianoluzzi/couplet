package com.couplesdating.couplet.ui.register

import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private var name = ""
    private var email = ""
    private var password = ""
    private var photo = ""
    private var gender = ""

    fun setEmailAndPassword(
        email: String,
        password: String
    ) {
        this.email = email
        this.password = password
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