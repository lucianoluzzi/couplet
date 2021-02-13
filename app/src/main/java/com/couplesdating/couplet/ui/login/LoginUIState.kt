package com.couplesdating.couplet.ui.login

sealed class LoginUIState {
    object EmailEmpty : LoginUIState()
    object PasswordEmpty : LoginUIState()
    object AuthError : LoginUIState()
    object Success : LoginUIState()
    object Loading : LoginUIState()
}
