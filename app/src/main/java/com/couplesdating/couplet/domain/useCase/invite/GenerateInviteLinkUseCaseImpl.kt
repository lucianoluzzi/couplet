package com.couplesdating.couplet.domain.useCase.invite

import android.net.Uri
import com.couplesdating.couplet.data.DynamicLinkProvider
import com.couplesdating.couplet.domain.model.InviteModel

class GenerateInviteLinkUseCaseImpl(
    private val dynamicLinkProvider: DynamicLinkProvider
) : GenerateInviteLinkUseCase {

    override suspend fun generateInviteLink(inviteModel: InviteModel): Uri {
        val inviteMap = mutableMapOf<String, String>().apply {
            put("inviterId", inviteModel.inviterId)
            put("inviteId", inviteModel.inviteId)
            put("inviterDisplayName", inviteModel.inviterDisplayName)
            if (!inviteModel.note.isNullOrBlank()) {
                put("note", inviteModel.note)
            }
        }

        return dynamicLinkProvider.generateUri(
            suffix = "partner",
            parameters = inviteMap
        )
    }
}