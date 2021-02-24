package com.couplesdating.couplet.ui.login.socialLogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentSocialLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
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
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { liveDataEvent ->

        }

        binding.withEmail.setOnClickListener { button ->
            val gotoLogin = SocialLoginFragmentDirections.actionSocialLoginFragmentToLoginFragment()
            button.findNavController().navigate(gotoLogin)
        }
        binding.withGoogle.setOnClickListener {
            loginWithGoogle()
        }
        binding.register.setOnClickListener { button ->
            val goToRegister =
                SocialLoginFragmentDirections.actionSocialLoginFragmentToEmailAndPasswordFragment()
            button.findNavController().navigate(goToRegister)
        }
    }

    private fun loginWithGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    val idToken = it.idToken
                    val displayName = it.displayName

                    if (idToken != null && displayName != null) {
                        firebaseAuthWithGoogle(idToken, displayName)
                    }
                }
            } catch (e: ApiException) {
                Log.w("LoginFragment", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String, displayName: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        viewModel.onGoogleSignIn(credential, displayName)
    }

    private companion object {
        private const val GOOGLE_SIGN_IN_REQUEST_CODE = 1234
    }
}