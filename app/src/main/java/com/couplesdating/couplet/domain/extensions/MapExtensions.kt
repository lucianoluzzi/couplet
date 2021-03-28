package com.couplesdating.couplet.domain.extensions

import java.net.URLEncoder

fun Map<String, String>.toUrlQueryString() = this.map { (key, value) ->
    "${URLEncoder.encode(key, "UTF-8")}=${URLEncoder.encode(value, "UTF-8")}"
}.joinToString("&")