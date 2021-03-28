package com.couplesdating.couplet.domain.useCase

import android.net.Uri
import com.couplesdating.couplet.data.DynamicLinkProvider
import com.couplesdating.couplet.ui.invite.InviteModel

class GenerateInviteLinkUseCaseImpl(
    private val dynamicLinkProvider: DynamicLinkProvider
) : GenerateInviteLinkUseCase {

    override suspend fun generateInviteLink(inviteModel: InviteModel): Uri {
        val dataMap = inviteModel.note?.let {
            mapOf(
                "id" to inviteModel.userIdentification,
                "note" to it
            )
        } ?: run {
            mapOf("id" to inviteModel.userIdentification)
        }

        return dynamicLinkProvider.generateUri(
            suffix = "partner",
            parameters = dataMap
        )
    }
}