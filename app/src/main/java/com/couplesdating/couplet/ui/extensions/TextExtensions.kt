package com.couplesdating.couplet.ui.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.internal.CheckableImageButton

fun EditText.textValue() = text.toString()
fun TextView.textValue() = text.toString()

fun ViewGroup.getPasswordToggleButton(): View? {
    val childCount = childCount
    for (index in childCount downTo 0) {
        val child = getChildAt(index)
        if (child is ViewGroup) {
            return child.getPasswordToggleButton()
        } else if (child is CheckableImageButton) {
            return child
        }
    }
    return null
}