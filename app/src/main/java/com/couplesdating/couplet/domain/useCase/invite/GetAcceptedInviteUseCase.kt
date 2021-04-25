package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.domain.model.AcceptedInvite

interface GetAcceptedInviteUseCase {
    fun getAcceptedInvite(): AcceptedInvite?
}