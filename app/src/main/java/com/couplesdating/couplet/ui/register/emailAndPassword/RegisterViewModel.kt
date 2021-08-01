package com.couplesdating.couplet.ui.register.emailAndPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.login.RegisterEvents
import com.couplesdating.couplet.data.repository.UserRepository
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.useCase.invite.AddInviteeIdUseCase
import com.couplesdating.couplet.domain.useCase.invite.DeleteInviteUseCase
import com.couplesdating.couplet.domain.useCase.invite.GetReceivedInviteUseCase
import com.couplesdating.couplet.domain.useCase.pair.FormPairUseCase
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import com.couplesdating.couplet.ui.utils.asLiveDataEvent
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val getReceivedInviteUseCase: GetReceivedInviteUseCase,
    private val formPairUseCase: FormPairUseCase,
    private val addInviteeIdUseCase: AddInviteeIdUseCase,
    private val deleteInviteUseCase: DeleteInviteUseCase,
    private val analytics: Analytics
) : ViewModel() {

    private val _emailScreenUIState = MutableLiveData<LiveDataEvent<EmailScreenUIState>>()
    val emailScreenUIState: LiveData<LiveDataEvent<EmailScreenUIState>> = _emailScreenUIState

    fun validate(
        email: String?,
        password: String?,
        confirmPassword: String?
    ) {
        analytics.trackEvent(RegisterEvents.RegisterClicked)
        setLiveDataValue(EmailScreenUIState.Loading)
        when {
            email.isNullOrBlank() -> {
                analytics.trackEvent(RegisterEvents.EmailEmpty)
                setLiveDataValue(EmailScreenUIState.EmailEmpty)
            }
            password.isNullOrBlank() -> {
                analytics.trackEvent(RegisterEvents.PasswordEmpty)
                setLiveDataValue(EmailScreenUIState.PasswordEmpty)
            }
            confirmPassword.isNullOrBlank() -> {
                analytics.trackEvent(RegisterEvents.ConfirmPasswordEmpty)
                setLiveDataValue(EmailScreenUIState.ConfirmPasswordEmpty)
            }
            password != confirmPassword -> {
                analytics.trackEvent(RegisterEvents.PasswordsDontMatch)
                setLiveDataValue(EmailScreenUIState.PasswordsDoesntMatch)
            }
            else -> register(
                email = email,
                password = password
            )
        }
    }

    private fun setLiveDataValue(uiState: EmailScreenUIState) {
        _emailScreenUIState.value = uiState.asLiveDataEvent
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
            if (registerResponse is Response.Error) {
                analytics.trackEvent(
                    RegisterEvents.RegisterError(registerResponse.errorMessage)
                )
                setLiveDataValue(
                    EmailScreenUIState.RegistrationError(
                        registerResponse.errorMessage ?: ""
                    )
                )
            } else {
                val currentUserId = if (registerResponse is Response.Success<*>) {
                    val user = (registerResponse as Response.Success<User>).data
                    user?.userId
                } else null
                formPairIfInviteAccepted(currentUserId)
                analytics.trackEvent(RegisterEvents.RegisterSuccess)
                setLiveDataValue(EmailScreenUIState.Success)
            }
        }
    }

    private suspend fun formPairIfInviteAccepted(currentUserId: String?) {
        val acceptedInviteUserId = getReceivedInviteUseCase.getReceivedInvite()
        acceptedInviteUserId?.let { invite ->
            if (invite.hasAccepted) {
                formPairUseCase.formPair(invite.inviterId)
                deleteInviteUseCase.deleteInvite(invite)
            } else {
                currentUserId?.let {
                    addInviteeIdUseCase.addInviteeId(
                        inviteeId = it,
                        inviteId = invite.inviteId,
                        inviterId = invite.inviterId,
                        inviterDisplayName = invite.inviterDisplayName,
                        note = invite.note
                    )
                }
            }
        }
    }

    fun onEmailInputClicked() {
        analytics.trackEvent(RegisterEvents.EmailInputClicked)
    }

    fun onPasswordInputClicked() {
        analytics.trackEvent(RegisterEvents.PasswordInputClicked)
    }

    fun onConfirmPasswordInputclicked() {
        analytics.trackEvent(RegisterEvents.ConfirmPasswordInputClicked)
    }

    fun onPasswordToggleClicked(isPasswordVisible: Boolean) {
        analytics.trackEvent(RegisterEvents.PasswordToggleClicked(isPasswordVisible))
    }

    fun onConfirmPasswordToggleClicked(isConfirmPasswordVisible: Boolean) {
        analytics.trackEvent(RegisterEvents.ConfirmPasswordToggleClicked(isConfirmPasswordVisible))
    }
}