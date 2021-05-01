package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel

class GetAInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : GetInviteUseCase {

    override fun getInvite(): InviteModel? {
        return inviteRepository.getPairInvite()
    }
}