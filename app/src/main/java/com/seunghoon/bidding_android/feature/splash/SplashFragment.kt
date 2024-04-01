package com.seunghoon.bidding_android.feature.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.seunghoon.bidding_android.databinding.FragmentSplashBinding
import com.seunghoon.bidding_android.navigation.navigateToSignIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val NAVIGATE_DELAY = 3000L

internal class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater)
        val navController = findNavController()
        lifecycleScope.launch {
            delay(NAVIGATE_DELAY)
            navController.navigateToSignIn()
        }

        return binding.root
    }
}
