package com.couplesdating.couplet.data

import android.net.Uri
import com.couplesdating.couplet.domain.extensions.toUrlQueryString
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase

class DynamicLinkProvider {

    fun getUri(
        suffix: String,
        parameters: Map<String, String>?
    ): Uri {
        val queryString = parameters?.toUrlQueryString() ?: ""
        val uriLink = "$DOMAIN/$suffix?$queryString"

        val dynamicLink = Firebase.dynamicLinks.dynamicLink {
            link = Uri.parse(uriLink)
            domainUriPrefix = DOMAIN
            androidParameters("com.couplesdating.couplet") { }
        }
        return dynamicLink.uri
    }

    companion object {
        private const val DOMAIN = "https://couplet.love"
    }
}