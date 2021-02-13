package com.couplesdating.couplet.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentNameAndGenderBinding

class NameAndGenderFragment : Fragment() {
    private lateinit var binding: FragmentNameAndGenderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNameAndGenderBinding.inflate(inflater, container, false)
        binding.gender.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.gender_item_layout,
                listOf("Female", "Male", "Other")
            )
        )
        return binding.root
    }
}