package com.couplesdating.couplet.ui.login.socialLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.login.SocialLoginEvents
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.useCase.auth.FacebookSignInUseCase
import com.couplesdating.couplet.domain.useCase.auth.GoogleSignInUseCase
import com.couplesdating.couplet.domain.useCase.invite.AddInviteeIdUseCase
import com.couplesdating.couplet.domain.useCase.invite.DeleteInviteUseCase
import com.couplesdating.couplet.domain.useCase.invite.GetReceivedInviteUseCase
import com.couplesdating.couplet.domain.useCase.pair.FormPairUseCase
import com.couplesdating.couplet.domain.useCase.user.GetCurrentUserUseCase
import com.couplesdating.couplet.ui.extensions.doNothing
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import com.couplesdating.couplet.ui.utils.asLiveDataEvent
import com.facebook.AccessToken
import kotlinx.coroutines.launch

class SocialLoginViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val facebookSignInUseCase: FacebookSignInUseCase,
    private val getReceivedInviteUseCase: GetReceivedInviteUseCase,
    private val deleteInviteUseCase: DeleteInviteUseCase,
    private val formPairUseCase: FormPairUseCase,
    private val addInviteeIdUseCase: AddInviteeIdUseCase,
    private val analytics: Analytics
) : ViewModel() {

    private val _userLiveData = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> = _userLiveData

    private val _uiStateLiveData = MutableLiveData<LiveDataEvent<SocialLoginUIState>>()
    val uiStateLiveData: LiveData<LiveDataEvent<SocialLoginUIState>> = _uiStateLiveData

    init {
        viewModelScope.launch {
            _userLiveData.value = getCurrentUserUseCase.getCurrentUser()
        }
    }

    fun onGoogleSignIn(idToken: String, displayName: String) {
        viewModelScope.launch {
            val response = googleSignInUseCase.signIn(
                idToken = idToken,
                displayName = displayName
            )

            when (response) {
                is Response.Success<*> -> doNothing
                is Response.Error -> onResponseError(response)
                is Response.Completed -> onSuccessResponse()
            }
        }
    }

    fun onFacebookSignIn(facebookAccessToken: AccessToken) {
        viewModelScope.launch {
            when (val response = facebookSignInUseCase.signIn(facebookAccessToken)) {
                is Response.Error -> onResponseError(response)
                is Response.Completed -> onSuccessResponse()
                is Response.Success<*> -> doNothing
            }
        }
    }

    private fun onResponseError(errorResponse: Response.Error) {
        setLiveDataValue(
            SocialLoginUIState.AuthError(
                errorResponse.errorMessage ?: ""
            )
        )
    }

    private suspend fun onSuccessResponse() {
        val loggedInUser = getCurrentUserUseCase.getCurrentUser()
        loggedInUser?.let { currentUser ->
            saveInvite(currentUser.userId)
            val userWithPartner = getCurrentUserUseCase.getCurrentUser() ?: currentUser
            _uiStateLiveData.postValue(
                LiveDataEvent(
                    SocialLoginUIState.Success(userWithPartner)
                )
            )
        }
    }

    private suspend fun saveInvite(currentUserId: String) {
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

    fun onLoginWithEmailClick() {
        analytics.trackEvent(SocialLoginEvents.LoginWithEmailCliked)
    }

    fun onLoginWithGoogleClick() {
        analytics.trackEvent(SocialLoginEvents.LoginWithGoogleClicked)
    }

    fun onLoginWithFacebookClicked() {
        _uiStateLiveData.value = LiveDataEvent(SocialLoginUIState.Loading)
        analytics.trackEvent(SocialLoginEvents.LoginWithFacebookClicked)
    }

    fun onTermsOfUsageClicked() {
        analytics.trackEvent(SocialLoginEvents.TermsOfUsageClicked)
    }

    private fun setLiveDataValue(uiState: SocialLoginUIState) {
        _uiStateLiveData.value = uiState.asLiveDataEvent
    }
}