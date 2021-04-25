package com.couplesdating.couplet.domain.useCase.invite

import android.net.Uri
import com.couplesdating.couplet.domain.model.InviteModel

interface GenerateInviteLinkUseCase {
    suspend fun generateInviteLink(inviteModel: InviteModel): Uri
}