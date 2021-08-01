package com.couplesdating.couplet.ui.login.emailLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.onboarding.LoginEvents
import com.couplesdating.couplet.domain.useCase.auth.SignInUseCase
import com.couplesdating.couplet.domain.useCase.invite.AddInviteeIdUseCase
import com.couplesdating.couplet.domain.useCase.invite.DeleteInviteUseCase
import com.couplesdating.couplet.domain.useCase.invite.GetReceivedInviteUseCase
import com.couplesdating.couplet.domain.useCase.pair.FormPairUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInUseCase: SignInUseCase,
    private val getReceivedInviteUseCase: GetReceivedInviteUseCase,
    private val deleteInviteUseCase: DeleteInviteUseCase,
    private val formPairUseCase: FormPairUseCase,
    private val addInviteeIdUseCase: AddInviteeIdUseCase,
    private val analytics: Analytics
) : ViewModel() {

    private val _uiStateLiveData = MutableLiveData<LoginUIState>()
    val uiStateLiveData: LiveData<LoginUIState> = _uiStateLiveData

    fun onLoginClicked(email: String?, password: String?) {
        analytics.trackEvent(LoginEvents.LoginClicked)
        _uiStateLiveData.value = LoginUIState.Loading
        when {
            email.isNullOrBlank() -> {
                analytics.trackEvent(LoginEvents.EmailEmptyState)
                _uiStateLiveData.value = LoginUIState.EmailEmpty
            }
            password.isNullOrBlank() -> {
                analytics.trackEvent(LoginEvents.PasswordEmptyState)
                _uiStateLiveData.value = LoginUIState.PasswordEmpty
            }
            else -> doSignIn(email, password)
        }
    }

    private fun doSignIn(email: String, password: String) {
        viewModelScope.launch {
            val signedInUser = signInUseCase.signIn(email, password)
            signedInUser?.let {
                handleExistingInvite(it.userId)
                analytics.trackEvent(LoginEvents.SuccessState)
                _uiStateLiveData.value = LoginUIState.Success
            } ?: run {
                analytics.trackEvent(LoginEvents.AuthenticationErrorState)
                _uiStateLiveData.value = LoginUIState.AuthError
            }
        }
    }

    private suspend fun handleExistingInvite(currentUserId: String) {
        val savedPairInvite = getReceivedInviteUseCase.getReceivedInvite()
        savedPairInvite?.let { invite ->
            if (invite.hasAccepted) {
                formPairUseCase.formPair(invite.inviterId)
                deleteInviteUseCase.deleteInvite(invite)
            } else {
                addInviteeIdUseCase.addInviteeId(
                    inviteeId = currentUserId,
                    inviteId = invite.inviteId,
                    inviterId = invite.inviterId,
                    inviterDisplayName = invite.inviterDisplayName,
                    note = invite.note
                )
            }
        }
    }

    fun onForgotPasswordClicked() {
        analytics.trackEvent(LoginEvents.ForgotPasswordClicked)
    }

    fun onEmailInputClicked() {
        analytics.trackEvent(LoginEvents.EmailClicked)
    }

    fun onPasswordInputClicked() {
        analytics.trackEvent(LoginEvents.PasswordClicked)
    }

    fun onRegisterClicked() {
        analytics.trackEvent(LoginEvents.RegisterClicked)
    }
}