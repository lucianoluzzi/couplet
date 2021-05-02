package com.couplesdating.couplet.domain.useCase.pair

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.data.repository.PairRepository
import com.couplesdating.couplet.domain.model.User
import kotlinx.coroutines.flow.map

class ShouldShowSyncUseCaseImpl(
    private val inviteRepository: InviteRepository,
    private val pairRepository: PairRepository
) : ShouldShowSyncUseCase {

    override suspend fun invoke(currentUser: User?) =
        pairRepository.shouldShowSync().map { shouldShowSync ->
            shouldShowSync && currentUser?.let { user ->
                currentUser.pairedPartner == null && inviteRepository.getReceivedPairInvite(user.userId) == null
            } ?: true
        }
}