package com.couplesdating.couplet.ui.extensions

import android.content.Context
import android.text.format.DateFormat
import com.google.firebase.Timestamp
import java.util.*

fun Date.getMediumFormatString(context: Context): String {
    val mediumDateFormatter = DateFormat.getMediumDateFormat(context)
    return mediumDateFormatter.format(this)
}

fun String.getDateFromTimestamp(context: Context): Date? {
    val mediumDateFormatter = DateFormat.getMediumDateFormat(context)
    return mediumDateFormatter.parse(this)
}