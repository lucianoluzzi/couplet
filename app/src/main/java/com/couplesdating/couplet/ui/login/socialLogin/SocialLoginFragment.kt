package com.couplesdating.couplet.ui.login.socialLogin

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentSocialLoginBinding
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.setUnderline
import com.couplesdating.couplet.ui.extensions.textValue
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes.SIGN_IN_CANCELLED
import com.google.android.gms.common.api.ApiException
import org.koin.androidx.viewmodel.ext.android.viewModel

class SocialLoginFragment : Fragment() {
    private val viewModel by viewModel<SocialLoginViewModel>()
    private lateinit var binding: FragmentSocialLoginBinding

    private lateinit var googleSignInResult: ActivityResultLauncher<Intent>
    private val callbackManager by lazy {
        CallbackManager.Factory.create()
    }

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
                            viewModel.onGoogleSignIn(idToken, displayName)
                        }
                    }
                } catch (e: ApiException) {
                    if (e.statusCode != SIGN_IN_CANCELLED) {
                        binding.loadingContainer.isVisible = false
                        goToError(e.cause?.message)
                    }
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
            handleUIState(liveDataEvent.getContentIfNotHandled())
        }

        with(binding) {
            withEmail.setOnClickListener { button ->
                viewModel.onLoginWithEmailClick()
                val gotoLogin =
                    SocialLoginFragmentDirections.actionSocialLoginFragmentToLoginFragment()
                button.findNavController().navigate(gotoLogin)
            }
            withGoogle.setOnClickListener {
                viewModel.onLoginWithGoogleClick()
                loginWithGoogle()
            }
            withFacebook.setOnClickListener {
                viewModel.onLoginWithFacebookClicked()
                loginWithFacebook()
            }
            termsOfUsage.setOnClickListener {
                viewModel.onTermsOfUsageClicked()
                goToTermsOfUsage()
            }
        }

        decorateTexts()
    }

    private fun handleUIState(uiState: SocialLoginUIState?) {
        if (uiState == null) {
            return
        }

        when (uiState) {
            is SocialLoginUIState.Success -> {
                binding.loadingContainer.isVisible = false
                goToHome(uiState.user)
            }
            is SocialLoginUIState.AuthError -> {
                binding.loadingContainer.isVisible = false
                goToError(uiState.error)
            }
            SocialLoginUIState.Loading -> {
                binding.loadingContainer.isVisible = true
            }
        }
    }

    private fun goToHome(user: User?) {
        val toHome =
            SocialLoginFragmentDirections.actionSocialLoginFragmentToHomeFragment(user)
        findNavController().navigate(toHome)
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

    private fun loginWithFacebook() {
        val loginManager = LoginManager.getInstance()
        loginManager.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    loginResult?.let {
                        viewModel.onFacebookSignIn(it.accessToken)
                    }
                }

                override fun onCancel() {
                    binding.loadingContainer.isVisible = false
                }

                override fun onError(exception: FacebookException) {
                    binding.loadingContainer.isVisible = false
                    goToError(exception.message)
                }
            }
        )
        loginManager.logInWithReadPermissions(
            this, arrayListOf("public_profile", "email")
        )
    }

    private fun decorateTexts() {
        decorateWelcome()
        decorateTermsOfUsage()
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

    private fun decorateTermsOfUsage() {
        val termsOfUsageText = binding.termsOfUsage.textValue()

        val spannable = SpannableString(termsOfUsageText)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        spannable.setFont(
            typeface = medium,
            wordToDecorate = "Privacy Policy",
            wholeText = termsOfUsageText
        )
        spannable.setUnderline(
            wordToDecorate = "Privacy Policy",
            wholeText = termsOfUsageText
        )
        binding.termsOfUsage.text = spannable
    }

    private fun goToTermsOfUsage() {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://couplet.flycricket.io/privacy.html"))
        startActivity(browserIntent)
    }

    private fun goToError(errorMessage: String? = null) {
        val bundle = errorMessage?.let {
            bundleOf("error" to errorMessage)
        }
        findNavController().navigate(R.id.errorFragment, bundle)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}