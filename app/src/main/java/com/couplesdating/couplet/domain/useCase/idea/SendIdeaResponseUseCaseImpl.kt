package com.couplesdating.couplet.domain.useCase.idea

import com.couplesdating.couplet.data.repository.IdeaRepository
import com.couplesdating.couplet.domain.model.UserResponse
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.request.SendIdeaRequest
import com.couplesdating.couplet.domain.useCase.user.GetCurrentUserUseCase

class SendIdeaResponseUseCaseImpl(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val ideaRepository: IdeaRepository
) : SendIdeaResponseUseCase {

    override suspend fun sendIdeaResponse(ideaId: String, userResponse: UserResponse): Response {
        val currentUser = getCurrentUserUseCase.getCurrentUser()
        currentUser?.let {
            val request = SendIdeaRequest(
                ideaId = ideaId,
                userId = currentUser.userId,
                userResponse = userResponse
            )
            return ideaRepository.sendIdeaResponse(request)
        }
        return Response.Error("Current user is null")
    }
}