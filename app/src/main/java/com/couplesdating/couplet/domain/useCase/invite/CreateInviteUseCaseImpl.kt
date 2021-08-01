package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.model.User
import java.util.*

class CreateInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : CreateInviteUseCase {

    override suspend fun createInvite(
        currentUser: User,
        inviteeDisplayName: String,
        inviteNote: String?
    ): Response {
        val currentUserDisplayName = when {
            !currentUser.name.isNullOrBlank() -> currentUser.name
            !currentUser.email.isNullOrBlank() -> currentUser.email
            else -> ""
        }
        val inviteModel = InviteModel(
            inviterId = currentUser.userId,
            inviterDisplayName = currentUserDisplayName,
            inputInviteeDisplayName = inviteeDisplayName,
            note = inviteNote,
            inviteId = currentUser.userId.hashCode().toString(),
            timestamp = Date()
        )

        return try {
            inviteRepository.saveInvite(inviteModel)
            Response.Success(inviteModel)
        } catch (exception: Exception) {
            Response.Error(exception.message)
        }
    }
}