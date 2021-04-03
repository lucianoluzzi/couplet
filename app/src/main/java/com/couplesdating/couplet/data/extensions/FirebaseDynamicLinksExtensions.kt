package com.couplesdating.couplet.data.extensions

import android.net.Uri
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun FirebaseDynamicLinks.generateShortenedUri(
    domain: String,
    uriLink: String,
    packageName: String
): Uri? {
    return suspendCancellableCoroutine { continuation ->
        shortLinkAsync {
            link = Uri.parse(uriLink)
            domainUriPrefix = domain
            androidParameters(packageName) { }
        }.addOnSuccessListener { task ->
            continuation.resume(task.shortLink)
        }.addOnFailureListener {
            continuation.resume(null)
        }
    }
}