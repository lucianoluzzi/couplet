package com.couplesdating.couplet.ui.register.emailAndPassword

sealed class EmailScreenUIState {
    object EmailEmpty : EmailScreenUIState()
    object PasswordEmpty : EmailScreenUIState()
    object ConfirmPasswordEmpty : EmailScreenUIState()
    object PasswordsDoesntMatch : EmailScreenUIState()
    object Success : EmailScreenUIState()
    data class RegistrationError(val errorMessage: String) : EmailScreenUIState()
}
