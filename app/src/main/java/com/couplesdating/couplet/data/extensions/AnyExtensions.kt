package com.couplesdating.couplet.data.extensions

inline fun <reified T> Any?.isInstanceOf(): Boolean {
    return this is T
}