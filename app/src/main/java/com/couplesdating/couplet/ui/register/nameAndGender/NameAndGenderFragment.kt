package com.couplesdating.couplet.ui.register.nameAndGender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentNameAndGenderBinding
import com.couplesdating.couplet.ui.extensions.textValue
import com.couplesdating.couplet.ui.register.RegisterViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class NameAndGenderFragment : Fragment() {
    private lateinit var binding: FragmentNameAndGenderBinding
    private val registerViewModel: RegisterViewModel by sharedViewModel()
    private val viewModel: NameAndGenderViewModel by viewModel()

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
        with(binding) {
            gender.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.gender_item_layout,
                    genderOptions
                )
            )
            gender.doOnTextChanged { text, _, _, _ ->
                binding.otherGenderInputLayout.isVisible = text.toString() == "Other"
            }
            register.setOnClickListener {
                clearErrors()
                viewModel.validate(
                    name = binding.name.textValue(),
                    gender = gender.textValue(),
                    otherGender = otherGender.textValue()
                )
            }
        }
    }

    private fun clearErrors() {
        binding.nameInputLayout.error = null
        binding.genderInputLayout.error = null
        binding.otherGenderInputLayout.error = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it.getContentIfNotHandled()) {
                NameAndGenderUIState.NameEmpty -> binding.nameInputLayout.error =
                    getString(R.string.empty_login_error)
                NameAndGenderUIState.GenderEmpty -> binding.genderInputLayout.error =
                    getString(R.string.empty_login_error)
                NameAndGenderUIState.OtherGenderEmpty -> binding.otherGenderInputLayout.error =
                    getString(R.string.empty_login_error)
                NameAndGenderUIState.Success -> {
                    goToPhotoFragment()
                }
            }
        }
    }

    private fun goToPhotoFragment() {
        val inputGender = if (binding.otherGenderInputLayout.isVisible) {
            binding.otherGender.textValue()
        } else {
            binding.gender.textValue()
        }
        registerViewModel.setNameAndGender(
            name = binding.name.textValue(),
            gender = inputGender
        )
        val nameAndGenderAction =
            NameAndGenderFragmentDirections.actionNameAndGenderFragmentToPhotoFragment()
        findNavController().navigate(nameAndGenderAction)
    }
}