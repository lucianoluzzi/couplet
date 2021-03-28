package com.couplesdating.couplet.domain.useCase

import android.net.Uri
import com.couplesdating.couplet.ui.invite.InviteModel

interface GenerateInviteLinkUseCase {
    fun generateInviteLink(inviteModel: InviteModel): Uri
}