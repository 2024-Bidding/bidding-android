package com.seunghoon.bidding_android.feature.likes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.seunghoon.bidding_android.R
import com.seunghoon.bidding_android.common.showToast
import com.seunghoon.bidding_android.databinding.FragmentLikesBinding
import com.seunghoon.bidding_android.feature.items.ItemsAdapter
import com.seunghoon.bidding_android.feature.items.ItemsSideEffect
import com.seunghoon.bidding_android.feature.items.ItemsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LikesFragment : Fragment() {

    private val binding by lazy {
        FragmentLikesBinding.inflate(layoutInflater)
    }

    private val viewModel: ItemsViewModel by viewModel()

    private val navController by lazy {
        requireActivity().findNavController(R.id.container_main)
    }

    private lateinit var likesAdapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        collectItemsSideEffect()
        viewModel.likes()

        likesAdapter = ItemsAdapter(
            items = mutableListOf(),
            navController = navController,
            viewModel = viewModel,
            isLikeAdapter = true,
        )

        return binding.root
    }

    private fun collectItemsSideEffect() {
        viewModel.collectSideEffect {
            when (it) {
                is ItemsSideEffect.Success -> {
                    likesAdapter.clearItems()
                    if (it.items.isNotEmpty()) {
                        binding.linearLikesEmpty.visibility = View.INVISIBLE
                        likesAdapter.addItems(it.items)
                        binding.rvLikes.adapter = likesAdapter
                    } else {
                        binding.linearLikesEmpty.visibility = View.VISIBLE
                    }
                }

                is ItemsSideEffect.Failure -> {
                    requireContext().showToast(it.message)
                }
            }
        }
    }
}
