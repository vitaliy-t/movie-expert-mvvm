package my.test.movieexpert.loginscreen.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import my.test.movieexpert.R
import my.test.movieexpert.databinding.FragmentSignInBinding
import my.test.movieexpert.loginscreen.model.state.SignInState
import my.test.movieexpert.loginscreen.viewmodel.SignInViewModel
import my.test.movieexpert.utilities.USER_PASSWORD_MIN_CHARACTERS
import my.test.movieexpert.utilities.alertDialogError
import my.test.movieexpert.utilities.hideKeyboard
import my.test.movieexpert.utilities.proceedToProfileScreen

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = signInViewModel

        signInViewModel.signInState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is SignInState.SignedIn -> {
                    proceedToProfileScreen(requireActivity())
                }
                is SignInState.Error -> {
                    alertDialogError(requireContext(), state.errorMessage)
                }
            }
        })

        binding.signInInputLayoutPassword.helperText = requireContext().getString(R.string.input_helper_min_password_length, USER_PASSWORD_MIN_CHARACTERS)

        binding.signInInputLayoutEmail.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) hideKeyboard() }
        binding.signInInputLayoutPassword.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) hideKeyboard() }

        binding.signInButtonGoToSignUp.setOnClickListener {
            this.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.signInButtonGoToForgotPassword.setOnClickListener {
            this.findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }

        return binding.root
    }
}