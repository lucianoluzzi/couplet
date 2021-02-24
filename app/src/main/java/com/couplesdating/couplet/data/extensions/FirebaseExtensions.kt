package com.couplesdating.couplet.data.extensions

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun FirebaseAuth.signIn(email: String, password: String): Task<AuthResult> {
    return suspendCancellableCoroutine { continuation ->
        signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            continuation.resume(task)
        }
    }
}

suspend fun FirebaseAuth.signIn(authCredential: AuthCredential): Task<AuthResult> {
    return suspendCancellableCoroutine { cancellableContinuation ->
        signInWithCredential(authCredential)
            .addOnCompleteListener { task ->
                cancellableContinuation.resume(task)
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