package com.couplesdating.couplet.ui.register.nameAndGender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentNameAndGenderBinding
import com.couplesdating.couplet.ui.register.RegisterViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NameAndGenderFragment : Fragment() {
    private lateinit var binding: FragmentNameAndGenderBinding
    private val viewModel: RegisterViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNameAndGenderBinding.inflate(inflater, container, false)
        setGenderField()
        return binding.root
    }

    private fun setGenderField() {
        val genderOptions = listOf("Female", "Male", "Other")
        binding.gender.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.gender_item_layout,
                genderOptions
            )
        )
        binding.gender.doOnTextChanged { text, _, _, _ ->
            binding.otherGenderInputLayout.isVisible = text.toString() == "Other"
        }
    }
}