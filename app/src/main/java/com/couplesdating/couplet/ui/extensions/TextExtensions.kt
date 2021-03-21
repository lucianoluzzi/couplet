package com.couplesdating.couplet.ui.extensions

import android.text.method.PasswordTransformationMethod
import android.widget.Checkable
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.textfield.TextInputLayout

fun EditText.textValue() = text.toString()
fun TextView.textValue() = text.toString()

fun TextInputLayout.setPasswordToggleClickListener(onClick: (isChecked: Boolean) -> Unit) {
    setEndIconOnClickListener { passwordToggleButton ->
        if (passwordToggleButton !is CheckableImageButton) {
            return@setEndIconOnClickListener
        }

        setCheckedState(passwordToggleButton, onClick)
        setPasswordVisibility()
    }
}

private fun setCheckedState(
    passwordToggleButton: CheckableImageButton,
    onClick: (isChecked: Boolean) -> Unit
) {
    with(passwordToggleButton as Checkable) {
        this.isChecked = !this.isChecked
        onClick(this.isChecked)
    }
}

private fun TextInputLayout.setPasswordVisibility() {
    val oldSelection = editText?.selectionEnd
    val passwordVisibility = editText?.transformationMethod !is PasswordTransformationMethod
    editText?.transformationMethod =
        PasswordTransformationMethod.getInstance().takeIf { passwordVisibility }
    oldSelection?.let {
        if (oldSelection >= 0) {
            editText?.setSelection(oldSelection)
        }
    }
}

