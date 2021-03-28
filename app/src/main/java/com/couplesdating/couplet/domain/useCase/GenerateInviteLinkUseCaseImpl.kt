package com.couplesdating.couplet.domain.useCase

import android.net.Uri
import com.couplesdating.couplet.data.DynamicLinkProvider
import com.couplesdating.couplet.ui.invite.InviteModel

class GenerateInviteLinkUseCaseImpl(
    private val dynamicLinkProvider: DynamicLinkProvider
) : GenerateInviteLinkUseCase {

    override fun generateInviteLink(inviteModel: InviteModel): Uri {
        val dataMap = inviteModel.note?.let {
            mapOf(
                "id" to inviteModel.userId,
                "note" to it
            )
        } ?: run {
            mapOf("id" to inviteModel.userId)
        }

        return dynamicLinkProvider.getUri(
            suffix = "partner",
            parameters = dataMap
        )
    }
}