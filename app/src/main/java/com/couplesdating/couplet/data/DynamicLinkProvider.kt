package com.couplesdating.couplet.data

import android.content.Context
import android.net.Uri
import com.couplesdating.couplet.R
import com.couplesdating.couplet.data.extensions.generateShortenedUri
import com.couplesdating.couplet.domain.extensions.toUrlQueryString
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase

class DynamicLinkProvider(
    private val context: Context
) {

    suspend fun generateUri(
        suffix: String,
        parameters: Map<String, String>?
    ): Uri {
        val domain = context.getString(R.string.dynamic_link_url)
        val queryString = parameters?.toUrlQueryString() ?: ""
        val uriLink = "$domain/$suffix?$queryString"
        return getUri(
            domain = domain,
            uriLink = uriLink
        )
    }

    private suspend fun getUri(
        domain: String,
        uriLink: String
    ): Uri {
        val packageName = context.getString(R.string.app_package)
        return Firebase.dynamicLinks.generateShortenedUri(
            domain = domain,
            uriLink = uriLink,
            packageName = packageName
        ) ?: run {
            Firebase.dynamicLinks.dynamicLink {
                link = Uri.parse(uriLink)
                domainUriPrefix = domain
                androidParameters(packageName) { }
            }.uri
        }
    }
}