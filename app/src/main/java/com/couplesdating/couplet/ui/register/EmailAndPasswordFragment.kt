package com.couplesdating.couplet.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.databinding.FragmentEmailPasswordBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class EmailAndPasswordFragment : Fragment() {
    private lateinit var binding: FragmentEmailPasswordBinding
    private val viewModel: RegisterViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }


}