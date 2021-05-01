package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User
import java.util.*

class CreateInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : CreateInviteUseCase {

    override suspend fun createInvite(
        currentUser: User,
        displayName: String,
        inviteNote: String?
    ): Response {
        val currentUserDisplayName = when {
            !currentUser.name.isNullOrBlank() -> currentUser.name
            !currentUser.email.isNullOrBlank() -> currentUser.email
            else -> ""
        }
        val inviterDisplayName = InviteModel(
            inviterId = currentUser.userId,
            inviterDisplayName = currentUserDisplayName,
            inputInviteeDisplayName = displayName,
            note = inviteNote,
            inviteId = currentUser.userId.hashCode().toString(),
            timestamp = Date()
        )

        return try {
            inviteRepository.saveInvite(inviterDisplayName)
            Response.Success(inviterDisplayName)
        } catch (exception: Exception) {
            Response.Error(exception.message)
        }
    }
}