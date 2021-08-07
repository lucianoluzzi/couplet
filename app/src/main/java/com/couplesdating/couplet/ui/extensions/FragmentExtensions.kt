package com.couplesdating.couplet.ui.extensions

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.R

fun Fragment.showError(errorMessage: String? = null) {
    val bundle = errorMessage?.let {
        bundleOf("error" to errorMessage)
    }
    findNavController().navigate(R.id.errorFragment, bundle)
}

fun Fragment.hideKeyboard() {
    requireView().let {
        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.showAlertDialog(
    title: String,
    message: String? = null,
    positiveButtonText: String,
    negativeButtonText: String? = null,
    negativeButtonClickAction: (() -> Unit)? = null,
    positiveButtonClickAction: () -> Unit
) {
    val negativeButtonText = negativeButtonText ?: "Cancel"
    val builder = AlertDialog.Builder(requireContext())
    builder.setTitle(title)
    builder.setPositiveButton(positiveButtonText) { dialog, _ ->
        positiveButtonClickAction()
        dialog.dismiss()
    }
    negativeButtonClickAction?.let {
        builder.setNegativeButton(negativeButtonText) { dialog, _ ->
            it()
            dialog.dismiss()
        }
    } ?: run {
        builder.setNegativeButton(negativeButtonText) { dialog, _ ->
            dialog.dismiss()
        }
    }
    message?.let {
        builder.setMessage(it)
    }
    builder.show()
}