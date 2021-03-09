package com.couplesdating.couplet.ui.extensions

import android.graphics.Typeface
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.text.style.UnderlineSpan
import androidx.annotation.ColorInt
import com.couplesdating.couplet.ui.widgets.FontSpan

fun Spannable.setColor(
    @ColorInt color: Int,
    wordToDecorate: String,
    wholeText: String
): Spannable {
    if (!wholeText.contains(wordToDecorate)) {
        return this
    }

    val colorSpan = ForegroundColorSpan(color)
    val startIndex = wholeText.indexOf(wordToDecorate)
    val endIndex = startIndex + wordToDecorate.length

    setSpan(
        colorSpan,
        startIndex,
        endIndex,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )

    return this
}

fun Spannable.setFont(
    typeface: Typeface,
    wordToDecorate: String,
    wholeText: String
): Spannable {
    if (!wholeText.contains(wordToDecorate)) {
        return this
    }

    val startIndex = wholeText.indexOf(wordToDecorate)
    val endIndex = startIndex + wordToDecorate.length

    val typefaceSpan = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
        TypefaceSpan(typeface)
    } else {
        FontSpan(typeface)
    }

    setSpan(
        typefaceSpan,
        startIndex,
        endIndex,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )

    return this
}

fun Spannable.setUnderline(
    wordToDecorate: String,
    wholeText: String
): Spannable {
    if (!wholeText.contains(wordToDecorate)) {
        return this
    }

    val startIndex = wholeText.indexOf(wordToDecorate)
    val endIndex = startIndex + wordToDecorate.length

    val underlineSpan = UnderlineSpan()

    setSpan(
        underlineSpan,
        startIndex,
        endIndex,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )

    return this
}