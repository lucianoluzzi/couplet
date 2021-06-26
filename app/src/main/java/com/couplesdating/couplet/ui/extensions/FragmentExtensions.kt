package com.couplesdating.couplet.ui.extensions

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