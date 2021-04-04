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
    setSpan(
        colorSpan,
        wordToDecorate,
        wholeText
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

    val typefaceSpan = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
        TypefaceSpan(typeface)
    } else {
        FontSpan(typeface)
    }

    setSpan(
        typefaceSpan,
        wordToDecorate,
        wholeText
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

    val underlineSpan = UnderlineSpan()
    setSpan(
        underlineSpan,
        wordToDecorate,
        wholeText
    )
    return this
}

private fun Spannable.setSpan(
    span: Any,
    wordToDecorate: String,
    wholeText: String,
    flag: Int = Spannable.SPAN_INCLUSIVE_INCLUSIVE
) {
    val startIndex = wholeText.indexOf(wordToDecorate)
    val endIndex = startIndex + wordToDecorate.length
    setSpan(
        span,
        startIndex,
        endIndex,
        flag
    )
}