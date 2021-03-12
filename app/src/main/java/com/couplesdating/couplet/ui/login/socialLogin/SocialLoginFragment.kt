package com.couplesdating.couplet.ui.login.socialLogin

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentSocialLoginBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.setUnderline
import com.couplesdating.couplet.ui.extensions.textValue
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.android.viewmodel.ext.android.viewModel

class SocialLoginFragment : Fragment() {
    private val viewModel: SocialLoginViewModel by viewModel()
    private lateinit var binding: FragmentSocialLoginBinding

    private lateinit var googleSignInResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleSignInResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->

                val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
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

        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { liveDataEvent ->

        }

        with(binding) {
            withEmail.setOnClickListener { button ->
                val gotoLogin =
                    SocialLoginFragmentDirections.actionSocialLoginFragmentToLoginFragment()
                button.findNavController().navigate(gotoLogin)
            }
            withGoogle.setOnClickListener {
                loginWithGoogle()
            }
            register.setOnClickListener { button ->
                val goToRegister =
                    SocialLoginFragmentDirections.actionSocialLoginFragmentToEmailAndPasswordFragment()
                button.findNavController().navigate(goToRegister)
            }
        }

        decorateTexts()
    }

    private fun loginWithGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        val signInIntent = googleSignInClient.signInIntent
        googleSignInResult.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String, displayName: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        viewModel.onGoogleSignIn(credential, displayName)
    }

    private fun decorateTexts() {
        decorateWelcome()
        decorateRegister()
    }

    private fun decorateWelcome() {
        val backText = binding.back.textValue()

        val spannable = SpannableString(backText)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        val wordToDecorate = ":)"
        spannable.setColor(color, wordToDecorate, backText)
        spannable.setFont(medium, wordToDecorate, backText)

        binding.back.text = spannable
    }

    private fun decorateRegister() {
        val registerText = binding.register.textValue()

        val spannable = SpannableString(registerText)
        val color = requireContext().getColor(R.color.blue)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val wordToDecorate = "SIGN UP"
        spannable.setColor(color, wordToDecorate, registerText)
        spannable.setFont(medium, wordToDecorate, registerText)
        spannable.setUnderline(wordToDecorate, registerText)

        binding.register.text = spannable
    }
}