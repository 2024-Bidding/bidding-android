package com.seunghoon.bidding_android.feature.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seunghoon.bidding_android.databinding.FragmentItemsBinding

internal class ItemsFragment : Fragment() {

    private val binding by lazy {
        FragmentItemsBinding.inflate(layoutInflater)
    }

    private val navController by lazy {
        findNavController()
    }

    private val items: MutableList<Item> by lazy {
        mutableListOf()
    }

    private val itemsAdapter by lazy {
        ItemsAdapter(items)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        repeat(20) {
            items.add(
                Item(
                    id = 0,
                    name = "PS5",
                    currentPrice = 10000,
                    imageUrl = "what?",
                    biddingStatus = BiddingStatus.BEFORE_BIDDING,
                    endDate = "2024-01-01",
                ),
            )
        }
        binding.rvItems.adapter = itemsAdapter
        binding.rvItems.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
}
