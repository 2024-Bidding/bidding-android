package com.seunghoon.bidding_android.feature.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.seunghoon.bidding_android.R
import com.seunghoon.bidding_android.databinding.FragmentItemDetailsBinding
import com.seunghoon.bidding_android.navigation.NavArguments
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemDetailsFragment : Fragment() {

    private lateinit var binding: FragmentItemDetailsBinding

    private val navController by lazy {
        findNavController()
    }

    private val itemId by lazy {
        arguments?.getLong(NavArguments.ITEM_ID) ?: 0L
    }

    private val viewModel: ItemDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemDetailsBinding.inflate(inflater)
        handleItemDetailsSideEffect()
        setToolbarListener()

        viewModel.fetchItemDetails(itemId)


        return binding.root
    }

    private fun setToolbarListener() {
        binding.toolbarItemDetails.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }

    private fun handleItemDetailsSideEffect() {
        viewModel.collectSideEffect {
            when (it) {
                is ItemDetailsSideEffect.Success -> {
                    binding.details = it.details
                    binding.tvItemDetailsPrice.text = getString(R.string.item_details_current_price, it.details.currentPrice.toString())
                }

                is ItemDetailsSideEffect.Failure -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
