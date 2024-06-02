package com.seunghoon.bidding_android.feature.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.seunghoon.bidding_android.R
import com.seunghoon.bidding_android.databinding.FragmentRootBinding

class RootFragment : Fragment() {

    private lateinit var binding: FragmentRootBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRootBinding.inflate(inflater)
        setBottomNavigation()
        return binding.root
    }

    private fun setBottomNavigation() {
        @Suppress("DEPRECATION")
        binding.bnvRoot.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {}
                R.id.likes -> {}
                R.id.my_page -> {}
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}

