package com.couplesdating.couplet.ui.invite

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.domain.useCase.GenerateInviteLinkUseCase
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase
import com.couplesdating.couplet.ui.utils.LiveDataEvent

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
            val inviteModel = InviteModel(
                userId = it.userId,
                note = note
            )
            _deepLink.value =
                LiveDataEvent(generateInviteLinkUseCase.generateInviteLink(inviteModel))
        }
    }
}