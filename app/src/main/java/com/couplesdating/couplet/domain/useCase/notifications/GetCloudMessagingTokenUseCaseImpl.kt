package com.couplesdating.couplet.domain.useCase.notifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class GetCloudMessagingTokenUseCaseImpl(
    private val messagingService: FirebaseMessaging
) : GetCloudMessagingTokenUseCase {

    override suspend fun getCloudMessageRegistrationToken(): String? {
        return try {
            messagingService.token.await()
        } catch (exception: Exception) {
            Log.e("CLOUD_MESSAGE", exception.message ?: "Error generating token")
             null
        }
    }
}