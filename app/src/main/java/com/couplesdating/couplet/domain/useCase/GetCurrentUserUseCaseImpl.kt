package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.UserRepository
import com.couplesdating.couplet.domain.model.User

class GetCurrentUserUseCaseImpl(
    private val userRepository: UserRepository
) : GetCurrentUserUseCase {

    override fun getCurrentUser(): User? = userRepository.getCurrentUser()
}