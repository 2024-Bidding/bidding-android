package com.seunghoon.bidding_android.feature.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.seunghoon.bidding_android.common.showToast
import com.seunghoon.bidding_android.databinding.FragmentSignInBinding
import com.seunghoon.bidding_android.navigation.navigateToRoot
import com.seunghoon.bidding_android.navigation.navigateToSignUp
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SignInFragment : Fragment() {

    private val binding: FragmentSignInBinding by lazy {
        FragmentSignInBinding.inflate(layoutInflater)
    }

    private val signInViewModel: SignInViewModel by viewModel()

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        collectSignInSideEffect()
        setDoSignUpTextEvent()
        setSignInButtonEvent()

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        binding.unbind()
    }

    private fun collectSignInSideEffect() {
        signInViewModel.collectSideEffect {
            when (it) {
                is SignInSideEffect.Success -> {
                    requireContext().showToast(it.message)
                    navController.navigateToRoot()
                }

                is SignInSideEffect.InvalidPassword -> {
                    requireContext().showToast(it.message)
                }

                is SignInSideEffect.NotFoundEmail -> {
                    requireContext().showToast(it.message)
                }
            }
        }
    }

    private fun setDoSignUpTextEvent() = with(binding) {
        tvSignInDoSignUp.setOnClickListener {
            navController.navigateToSignUp()
        }
    }

    private fun setSignInButtonEvent() = with(binding) {
        btnSignInSignIn.setOnClickListener {
            signInViewModel.signIn(
                email = etSignInEmail.text.toString(),
                password = etSignInPassword.text.toString(),
            )
        }
    }
}
