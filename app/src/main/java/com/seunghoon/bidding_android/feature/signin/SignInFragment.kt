package com.seunghoon.bidding_android.feature.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.seunghoon.bidding_android.databinding.FragmentSignInBinding
import com.seunghoon.bidding_android.navigation.navigateToRoot
import com.seunghoon.bidding_android.navigation.navigateToSignUp
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    private val signInViewModel: SignInViewModel by viewModel()

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        collectSignInSideEffect()
        setDoSignUpTextEvent()
        setSignInButtonEvent()

        return binding.root
    }

    private fun collectSignInSideEffect() {
        signInViewModel.collectSideEffect {
            when (it) {
                is SignInSideEffect.Success -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    navController.navigateToRoot()
                }

                is SignInSideEffect.InvalidPassword -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is SignInSideEffect.NotFoundEmail -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setDoSignUpTextEvent() = with(binding) {
        val navController = findNavController()
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
