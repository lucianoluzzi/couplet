package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User

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
        val inviteModel = InviteModel(
            userId = currentUser.userId,
            currentUserDisplay = currentUserDisplayName,
            displayName = displayName,
            note = inviteNote,
            inviteId = currentUser.userId.hashCode().toString()
        )

        return try {
            inviteRepository.createInvite(inviteModel)
            Response.Success(inviteModel)
        } catch (exception: Exception) {
            Response.Error(exception.message)
        }
    }
}