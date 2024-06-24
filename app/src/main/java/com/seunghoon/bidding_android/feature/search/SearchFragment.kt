package com.seunghoon.bidding_android.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.seunghoon.bidding_android.R
import com.seunghoon.bidding_android.databinding.FragmentSearchBinding
import com.seunghoon.bidding_android.feature.items.ItemsAdapter
import com.seunghoon.bidding_android.feature.items.ItemsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {


    private val binding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }

    private val searchViewModel: SearchViewModel by viewModel()

    private val itemsViewModel: ItemsViewModel by viewModel()

    private val navController by lazy {
        requireActivity().findNavController(R.id.container_main)
    }

    private val itemsAdapter: ItemsAdapter by lazy {
        ItemsAdapter(
            items = searchViewModel.items,
            navController = navController,
            viewModel = itemsViewModel,
            isLikeAdapter = false,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initView()
        collectSearchSideEffect()

        return binding.root
    }

    private fun initView() {
        setSearchBar()
        setItemsAdapter()
    }

    private fun setSearchBar() {
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?) = false

            override fun onQueryTextChange(keyword: String?): Boolean {
                searchViewModel.updateKeyword(keyword)
                return true
            }
        })
    }
    private fun setItemsAdapter() {
        binding.rvSearch.let {
            it.adapter = itemsAdapter
        }
    }

    private fun collectSearchSideEffect() {
        lifecycleScope.launch {
            searchViewModel.sideEffect.collect {
                when (it) {
                    is SearchSideEffect.Success -> {
                        itemsAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}
