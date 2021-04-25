package com.couplesdating.couplet.domain.useCase.pair

import com.couplesdating.couplet.data.repository.PairRepository
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.useCase.user.GetCurrentUserUseCase

class FormPairUseCaseImpl(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val pairRepository: PairRepository
) : FormPairUseCase {

    override suspend fun formPair(partnerId: String): Response {
        val currentUser = getCurrentUserUseCase.getCurrentUser()
        currentUser?.let {
            return pairRepository.formPair(
                firstUserId = partnerId,
                secondUserId = it.userId
            )
        }

        return Response.Error("User not logged in")
    }
}