package com.seunghoon.bidding_android.feature.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.seunghoon.bidding_android.R
import com.seunghoon.bidding_android.databinding.FragmentItemDetailsBinding
import com.seunghoon.bidding_android.navigation.NavArguments
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("SetTextI18n")
class ItemDetailsFragment : Fragment() {

    private lateinit var binding: FragmentItemDetailsBinding

    private val navController by lazy {
        findNavController()
    }

    private val itemId by lazy {
        arguments?.getLong(NavArguments.ITEM_ID) ?: 0L
    }

    private val viewModel: ItemDetailsViewModel by viewModel()

    private lateinit var bidItemDialog: BidItemDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemDetailsBinding.inflate(inflater)
        handleItemDetailsSideEffect()
        setToolbarListener()
        setOnClickBidItemButton()

        viewModel.fetchItemDetails(itemId)

        return binding.root
    }

    private fun setViewPager(images: List<String>) {
        binding.vpItemDetailsImage.adapter = ItemImageAdapter(images = images)
    }

    private fun setToolbarListener() {
        binding.toolbarItemDetails.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }

    private fun setOnClickBidItemButton() {
        binding.btnItemDetailsBid.setOnClickListener {
            bidItemDialog = BidItemDialog(
                context = requireContext(),
                currentPrice = binding.details?.currentPrice ?: 0,
                maxPrice = binding.details?.maxPrice ?: 0,
                bidItemDialogListener = object : BidItemDialogListener {
                    override fun onBidItemClick(price: Long) {
                        viewModel.bidItem(
                            itemId = itemId,
                            price = price,
                        )
                    }
                }
            )
            with(bidItemDialog) {
                show()
                window?.setLayout(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT,
                )
            }
        }
    }

    private fun handleItemDetailsSideEffect() {
        viewModel.collectSideEffect {
            when (it) {
                is ItemDetailsSideEffect.Success -> {
                    with(binding) {
                        details = it.details
                        tvItemDetailsPrice.text = getString(
                            R.string.item_details_current_price,
                            it.details.currentPrice.toString()
                        )
                        tvItemDetailsPagerCounterText.text = "1/${it.details.imageUrls.size}"
                    }
                    setViewPager(it.details.imageUrls)
                    onSwipedImageAdapter()
                }

                is ItemDetailsSideEffect.BidSuccess -> {
                    Toast.makeText(requireContext(), "성공적으로 입찰되었습니다", Toast.LENGTH_SHORT).show()
                    viewModel.fetchItemDetails(itemId = itemId)
                    bidItemDialog.hide()
                }

                is ItemDetailsSideEffect.Failure -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onSwipedImageAdapter() {
        binding.vpItemDetailsImage.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding.tvItemDetailsPagerCounterText.let {
                    val max = it.text.split("/")[1]
                    it.text = "${position + 1}/$max"
                }
            }
        })
    }
}
