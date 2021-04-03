package com.couplesdating.couplet.data.extensions

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun FirebaseUser.updateUser(profileChangeRequest: UserProfileChangeRequest): Task<Void> {
    return suspendCancellableCoroutine { continuation ->
        updateProfile(profileChangeRequest).addOnCompleteListener { task ->
            continuation.resume(task)
        }
    }
}