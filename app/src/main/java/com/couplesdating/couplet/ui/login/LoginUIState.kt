package com.couplesdating.couplet.ui.login

sealed class LoginUIState {
    object EmailEmpty: LoginUIState()
    object PasswordEmpty: LoginUIState()
}
