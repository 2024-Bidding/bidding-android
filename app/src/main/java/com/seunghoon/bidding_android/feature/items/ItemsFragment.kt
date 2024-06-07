package com.seunghoon.bidding_android.feature.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seunghoon.bidding_android.R
import com.seunghoon.bidding_android.databinding.FragmentItemsBinding
import com.seunghoon.bidding_android.navigation.navigateToCreateItem
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ItemsFragment : Fragment() {

    private val binding by lazy {
        FragmentItemsBinding.inflate(layoutInflater)
    }

    private val viewModel: ItemsViewModel by viewModel()

    private val navController by lazy {
        requireActivity().findNavController(R.id.container_main)
    }

    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initView()
        collectItemsSideEffect()

        itemsAdapter = ItemsAdapter(
            items = mutableListOf(),
            navController = navController,
            viewModel = viewModel,
        )

        return binding.root
    }

    private fun collectItemsSideEffect() {
        viewModel.collectSideEffect {
            when (it) {
                is ItemsSideEffect.Success -> {
                    itemsAdapter.clearItems()
                    itemsAdapter.addItems(it.items)
                    binding.rvItems.adapter = itemsAdapter
                }
            }
        }
    }

    private fun initView() = with(binding) {
        rvItems.layoutManager = LinearLayoutManager(context)
        fabRegisterItem.setOnClickListener {
            navController.navigateToCreateItem()
        }
    }

    override fun onResume() {
        super.onResume()
        itemsAdapter.clearItems()
        viewModel.fetchItems()
    }
}
