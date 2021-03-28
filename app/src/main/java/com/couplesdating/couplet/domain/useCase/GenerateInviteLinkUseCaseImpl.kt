package com.couplesdating.couplet.domain.useCase

import android.net.Uri
import com.couplesdating.couplet.data.DynamicLinkProvider
import com.couplesdating.couplet.ui.invite.InviteModel

class GenerateInviteLinkUseCaseImpl(
    private val dynamicLinkProvider: DynamicLinkProvider
) : GenerateInviteLinkUseCase {

    override suspend fun generateInviteLink(inviteModel: InviteModel): Uri {
        val inviteMap = mutableMapOf<String, String>().apply {
            put("id", inviteModel.userId)

            if (!inviteModel.displayName.isNullOrBlank()) {
                put("displayName", inviteModel.displayName)
            }
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