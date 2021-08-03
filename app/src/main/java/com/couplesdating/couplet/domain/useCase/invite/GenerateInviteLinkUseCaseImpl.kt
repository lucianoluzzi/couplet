package com.couplesdating.couplet.domain.useCase.invite

import android.content.Context
import android.net.Uri
import com.couplesdating.couplet.data.DynamicLinkProvider
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.ui.extensions.getMediumFormatString
import com.google.firebase.Timestamp

class GenerateInviteLinkUseCaseImpl(
    private val dynamicLinkProvider: DynamicLinkProvider,
    private val context: Context
) : GenerateInviteLinkUseCase {

    override suspend fun generateInviteLink(inviteModel: InviteModel): Uri {
        val inviteMap = mutableMapOf<String, String>().apply {
            put("inviterId", inviteModel.inviterId)
            put("inviteId", inviteModel.inviteId)
            put("inviterDisplayName", inviteModel.inviterDisplayName)
            if (!inviteModel.note.isNullOrBlank()) {
                put("note", inviteModel.note)
            }
            inviteModel.timestamp?.let {
                put("timestamp", it.getMediumFormatString(context))
            }
        }

        return dynamicLinkProvider.generateUri(
            suffix = "partner",
            parameters = inviteMap
        )
    }
}