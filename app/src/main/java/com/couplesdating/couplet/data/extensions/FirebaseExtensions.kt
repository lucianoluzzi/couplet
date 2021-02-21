package com.couplesdating.couplet.data.extensions

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun FirebaseAuth.signIn(email: String, password: String): Task<AuthResult> {
    return suspendCancellableCoroutine { continuation ->
        signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            continuation.resume(task)
        }
    }
}

suspend fun FirebaseAuth.register(
    password: String,
    email: String
): Task<AuthResult> {
    return suspendCancellableCoroutine { continuation ->
        createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            continuation.resume(task)
        }
    }
}

suspend fun FirebaseUser.updateUser(profileChangeRequest: UserProfileChangeRequest): Task<Void> {
    return suspendCancellableCoroutine { continuation ->
        updateProfile(profileChangeRequest).addOnCompleteListener { task ->
            continuation.resume(task)
        }
    }
}

suspend fun FirebaseAuth.resetPassword(email: String): Task<Void> {
    return suspendCancellableCoroutine { continuation ->
        sendPasswordResetEmail(email).addOnCompleteListener { taskResult ->
            continuation.resume(taskResult)
        }
    }
}