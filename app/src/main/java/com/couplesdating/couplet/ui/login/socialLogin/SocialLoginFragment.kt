package com.couplesdating.couplet.ui.login.socialLogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.couplesdating.couplet.databinding.FragmentSocialLoginBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SocialLoginFragment : Fragment() {
    private val viewModel: SocialLoginViewModel by viewModel()
    private lateinit var binding: FragmentSocialLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSocialLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            user?.let {
                // already signed in
            }
        }

        binding.withEmail.setOnClickListener { button ->
            val gotoLogin = SocialLoginFragmentDirections.actionSocialLoginFragmentToLoginFragment()
            button.findNavController().navigate(gotoLogin)
        }
        binding.register.setOnClickListener { button ->
            val goToRegister =
                SocialLoginFragmentDirections.actionSocialLoginFragmentToEmailAndPasswordFragment()
            button.findNavController().navigate(goToRegister)
        }
    }
}