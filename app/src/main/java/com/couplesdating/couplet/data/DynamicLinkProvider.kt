package com.couplesdating.couplet.data

import android.net.Uri
import com.couplesdating.couplet.data.extensions.generateShortenedUri
import com.couplesdating.couplet.domain.extensions.toUrlQueryString
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase

class DynamicLinkProvider {

    suspend fun generateUri(
        suffix: String,
        parameters: Map<String, String>?
    ): Uri {
        val queryString = parameters?.toUrlQueryString() ?: ""
        val uriLink = "$DOMAIN/$suffix?$queryString"
        return getUri(uriLink)
    }

    private suspend fun getUri(uriLink: String): Uri {
        return Firebase.dynamicLinks.generateShortenedUri(
            domain = DOMAIN,
            uriLink = uriLink,
            packageName = PACKAGE_NAME
        ) ?: run {
            Firebase.dynamicLinks.dynamicLink {
                link = Uri.parse(uriLink)
                domainUriPrefix = DOMAIN
                androidParameters("com.couplesdating.couplet") { }
            }.uri
        }
    }

    companion object {
        private const val DOMAIN = "https://couplet.love"
        private const val PACKAGE_NAME = "com.couplesdating.couplet"
    }
}