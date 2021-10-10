package com.couplesdating.couplet.domain.useCase.invite

import android.content.Context
import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.ui.extensions.getDateFromTimestamp
import java.util.*

class SavePairInviteUseCaseImpl(
    private val inviteRepository: InviteRepository,
    private val context: Context
) : SavePairInviteUseCase {

    override fun savePairInvite(
        inviteId: String,
        inviterId: String,
        inviterDisplayName: String?,
        hasAccepted: Boolean,
        timestamp: String?
    ) {
        val invite = InviteModel(
            inviteId = inviteId,
            inviterId = inviterId,
            inviterDisplayName = inviterDisplayName ?: "",
            hasAccepted = hasAccepted,
            timestamp = Date()
        )
        inviteRepository.savePairInvite(invite)
    }
}