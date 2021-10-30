package com.couplesdating.couplet.ui.extensions

import android.content.Context
import android.content.Intent
import android.content.res.Configuration

fun Context.isDarkTheme(): Boolean {
    val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
}

fun Context.createShareIntent(message: String): Intent {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, message)
    return Intent.createChooser(intent, "Share")
}