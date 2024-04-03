package com.seunghoon.bidding_android.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.seunghoon.bidding_android.databinding.FragmentSignUpBinding
import com.seunghoon.bidding_android.navigation.navigateToSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    private lateinit var binding: FragmentSignUpBinding

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        collectSignUpSideEffect()

        setDoSignInTextEvent()
        setSignUpButtonEvent()
        return binding.root
    }

    private fun collectSignUpSideEffect() {
        signUpViewModel.collectSideEffect {
            when (it) {
                is SignUpSideEffect.Success -> {
                    Toast.makeText(context, "회원가입 되었습니다! 로그인 후 이용해주세요", Toast.LENGTH_SHORT).show()
                    navController.navigateToSignIn()
                }

                is SignUpSideEffect.EmailAlreadyExists -> {
                    Toast.makeText(context, "이미 존재하는 이메일이에요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setDoSignInTextEvent() = with(binding) {
        tvSignUpDoSignIn.setOnClickListener {
            navController.navigateToSignIn()
        }
    }

    private fun setSignUpButtonEvent() = with(binding) {
        btnSignInSignIn.setOnClickListener {
            signUpViewModel.signUp(
                email = etSignUpEmail.text.toString(),
                name = etSignUpName.text.toString(),
                password = etSignUpPassword.text.toString(),
            )
        }
    }
}
