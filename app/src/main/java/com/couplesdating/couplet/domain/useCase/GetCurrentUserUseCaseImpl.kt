package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.UserRepository
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User

class GetCurrentUserUseCaseImpl(
    private val getPartnerUseCase: GetPartnerUseCase,
    private val userRepository: UserRepository
) : GetCurrentUserUseCase {

    override suspend fun getCurrentUser(): User? {
        val currentUser = userRepository.getCurrentUser()
        return currentUser?.let { user ->
            val partnerResponse = getPartnerUseCase.getPartner(user.userId)
            val userWithPartner = if (partnerResponse is Response.Success<*>) {
                val partner = partnerResponse.data
                currentUser.copy(
                    pairedPartner = partner as User
                )
            } else {
                currentUser
            }
            userWithPartner
        }
    }
}