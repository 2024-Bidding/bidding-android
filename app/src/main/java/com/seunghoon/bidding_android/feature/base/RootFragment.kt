package com.seunghoon.bidding_android.feature.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.seunghoon.bidding_android.R
import com.seunghoon.bidding_android.databinding.FragmentRootBinding
import com.seunghoon.bidding_android.feature.items.ItemsFragment

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
        childFragmentManager.commit {
            add<ItemsFragment>(R.id.frame_root)
        }
        @Suppress("DEPRECATION")
        binding.bnvRoot.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    childFragmentManager.beginTransaction().commit()
                }

                R.id.likes -> {

                }

                R.id.my_page -> {

                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}

