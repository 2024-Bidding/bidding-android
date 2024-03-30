package com.seunghoon.bidding_android.feature.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.seunghoon.bidding_android.databinding.FragmentSignInBinding

internal class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }
}
