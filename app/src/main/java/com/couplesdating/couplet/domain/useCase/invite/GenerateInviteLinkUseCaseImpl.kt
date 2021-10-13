package com.couplesdating.couplet.domain.useCase.invite

import android.content.Context
import android.net.Uri
import com.couplesdating.couplet.data.DynamicLinkProvider
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.ui.extensions.getMediumFormatString
import com.google.firebase.crashlytics.FirebaseCrashlytics

class GenerateInviteLinkUseCaseImpl(
    private val dynamicLinkProvider: DynamicLinkProvider,
    private val context: Context
) : GenerateInviteLinkUseCase {

    override suspend fun generateInviteLink(inviteModel: InviteModel): Uri {
        val inviteMap = mutableMapOf<String, String>().apply {
            put("inviterId", inviteModel.inviterId)
            put("inviteId", inviteModel.inviteId)
            put("inviterDisplayName", inviteModel.inviterDisplayName)
            try {
                inviteModel.timestamp?.let {
                    put("timestamp", it.getMediumFormatString(context))
                }
            } catch (exception: Exception) {
                FirebaseCrashlytics.getInstance().recordException(exception)
            }
        }

        return dynamicLinkProvider.generateUri(
            suffix = "partner",
            parameters = inviteMap
        )
    }
}