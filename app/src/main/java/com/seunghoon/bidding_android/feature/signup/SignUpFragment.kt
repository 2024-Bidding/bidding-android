package com.seunghoon.bidding_android.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.seunghoon.bidding_android.databinding.FragmentSignUpBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }
}
