package com.seunghoon.bidding_android.feature.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.seunghoon.bidding_android.R
import com.seunghoon.bidding_android.databinding.FragmentRootBinding

class RootFragment : Fragment() {

    private val binding: FragmentRootBinding by lazy {
        FragmentRootBinding.inflate(layoutInflater)
    }

    private val navController by lazy {
        childFragmentManager.findFragmentById(R.id.container_root_bottom_navigation)
            ?.findNavController()!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setBottomNavigation()
        return binding.root
    }

    private fun setBottomNavigation() {
        binding.bnvRoot.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}

