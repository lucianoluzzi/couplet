package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.PairRepository
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.ui.invite.InviteModel

class CreateInviteUseCaseImpl(
    private val pairRepository: PairRepository
) : CreateInviteUseCase {

    override suspend fun createInvite(
        currentUser: User,
        inviteNote: String?
    ): Response {
        val displayName = when {
            !currentUser.name.isNullOrBlank() -> currentUser.name
            !currentUser.email.isNullOrBlank() -> currentUser.email
            else -> ""
        }
        val inviteModel = InviteModel(
            userId = currentUser.userId,
            displayName = displayName,
            note = inviteNote,
            inviteId = currentUser.userId.hashCode().toString()
        )

        return when (val response = pairRepository.createInvite(inviteModel)) {
            is Response.Error -> response
            is Response.Completed -> Response.Success(inviteModel)
            is Response.Success<*> -> Response.Success(inviteModel)
        }
    }
}