package com.couplesdating.couplet.data.extensions

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun <K, V> CollectionReference.insert(dataMap: Map<K, V>): Task<DocumentReference> {
    return suspendCancellableCoroutine { continuation ->
        add(dataMap).addOnCompleteListener { task ->
            continuation.resume(task)
        }
    }
}