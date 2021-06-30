package com.couplesdating.couplet.domain.useCase.notifications

interface GetCloudMessagingTokenUseCase {
    suspend fun getCloudMessageRegistrationToken(): String?
}