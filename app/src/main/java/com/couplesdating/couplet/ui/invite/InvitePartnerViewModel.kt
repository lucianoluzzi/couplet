package com.couplesdating.couplet.ui.invite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.useCase.CreateInviteUseCase
import com.couplesdating.couplet.domain.useCase.GenerateInviteLinkUseCase
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase
import com.couplesdating.couplet.ui.extensions.doNothing
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import kotlinx.coroutines.launch

class InvitePartnerViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val generateInviteLinkUseCase: GenerateInviteLinkUseCase,
    private val createInviteUseCase: CreateInviteUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val _deepLink = MutableLiveData<LiveDataEvent<InvitePartnerUIState>>()
    val deepLink: LiveData<LiveDataEvent<InvitePartnerUIState>> = _deepLink

    fun createInviteLink(note: String?) {
        val currentUser = getCurrentUserUseCase.getCurrentUser()
        currentUser?.let {
            viewModelScope.launch {
                val inviteResponse = createInviteUseCase.createInvite(currentUser, note)
                when (inviteResponse) {
                    is Response.Completed -> doNothing
                    is Response.Error -> {
                        _deepLink.value = LiveDataEvent(
                            InvitePartnerUIState.Error(
                                inviteResponse.errorMessage ?: ""
                            )
                        )
                    }
                    is Response.Success<*> -> {
                        if (inviteResponse.data is InviteModel) {
                            val generatedInviteLink =
                                generateInviteLinkUseCase.generateInviteLink(inviteResponse.data)
                            _deepLink.value =
                                LiveDataEvent(InvitePartnerUIState.Success(generatedInviteLink.toString()))
                        }
                    }
                }
            }
        }
    }
}