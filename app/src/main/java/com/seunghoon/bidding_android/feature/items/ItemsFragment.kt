package com.seunghoon.bidding_android.feature.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.seunghoon.bidding_android.databinding.FragmentItemsBinding
import java.text.DecimalFormat

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
                    currentPrice = DecimalFormat("#,###").format(10000),
                    imageUrl = "https://image-cdn.hypb.st/https%3A%2F%2Fkr.hypebeast.com%2Ffiles%2F2024%2F02%2Fsony-says-the-ps5-is-entering-the-latter-stage-of-its-life-cycle-01.jpg?cbr=1&q=90",
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
