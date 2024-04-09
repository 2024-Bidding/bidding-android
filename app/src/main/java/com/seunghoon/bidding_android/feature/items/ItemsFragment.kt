package com.seunghoon.bidding_android.feature.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seunghoon.bidding_android.databinding.FragmentItemsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ItemsFragment : Fragment() {

    private val binding by lazy {
        FragmentItemsBinding.inflate(layoutInflater)
    }

    private val viewModel: ItemsViewModel by viewModel()

    private val navController by lazy {
        findNavController()
    }

    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.collectSideEffect {
            when (it) {
                is ItemsSideEffect.Success -> {
                    itemsAdapter = ItemsAdapter(it.items)
                    binding.rvItems.adapter = itemsAdapter
                }
            }
        }
        binding.rvItems.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
}
