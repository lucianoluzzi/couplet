package com.couplesdating.couplet.ui.extensions

import android.view.View

fun View.onGetFocus(onFocus: () -> Unit) {
    setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            onFocus()
        }
    }
}