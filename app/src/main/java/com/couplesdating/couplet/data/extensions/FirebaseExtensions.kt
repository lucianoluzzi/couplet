package com.couplesdating.couplet.data.extensions

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun FirebaseAuth.signIn(email: String, password: String): Task<AuthResult> {
    return suspendCoroutine { continuation ->
        signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            continuation.resume(task)
        }
    }
}