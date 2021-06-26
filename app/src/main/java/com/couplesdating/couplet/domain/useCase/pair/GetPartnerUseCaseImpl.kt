package com.couplesdating.couplet.domain.useCase.pair

import com.couplesdating.couplet.data.repository.PairRepository
import com.couplesdating.couplet.domain.network.Response
import java.lang.Exception

class GetPartnerUseCaseImpl(
    private val pairRepository: PairRepository
) : GetPartnerUseCase {

    override suspend fun getPartner(currentUserId: String): Response {
        return try {
            pairRepository.getPartner(currentUserId)
        } catch (exception: Exception) {
            Response.Error(exception.message)
        }
    }
}