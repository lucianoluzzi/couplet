package com.couplesdating.couplet.ui.invite

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.domain.useCase.GenerateInviteLinkUseCase
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import kotlinx.coroutines.launch

class InvitePartnerViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val generateInviteLinkUseCase: GenerateInviteLinkUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val _deepLink = MutableLiveData<LiveDataEvent<Uri>>()
    val deepLink: LiveData<LiveDataEvent<Uri>> = _deepLink

    fun createInviteLink(note: String?) {
        val currentUser = getCurrentUserUseCase.getCurrentUser()
        currentUser?.let {
            val displayName = when {
                !it.name.isNullOrBlank() -> it.name
                !it.email.isNullOrBlank() -> it.email
                else -> ""
            }
            val inviteModel = InviteModel(
                userId = it.userId,
                displayName = displayName,
                note = note
            )

            viewModelScope.launch {
                _deepLink.value =
                    LiveDataEvent(generateInviteLinkUseCase.generateInviteLink(inviteModel))
            }
        }
    }
}