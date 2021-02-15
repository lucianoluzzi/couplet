package com.couplesdating.couplet.ui.register

sealed class EmailScreenUIState {
    object EmailEmpty : EmailScreenUIState()
    object PasswordEmpty : EmailScreenUIState()
    object ConfirmPasswordEmpty : EmailScreenUIState()
    object PasswordsDoesntMatch : EmailScreenUIState()
    object Success : EmailScreenUIState()
}
