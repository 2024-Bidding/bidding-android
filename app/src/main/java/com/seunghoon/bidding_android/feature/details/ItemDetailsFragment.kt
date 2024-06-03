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
import com.seunghoon.bidding_android.common.showToast
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
            binding.details?.run {
                bidItemDialog = BidItemDialog(
                    context = requireContext(),
                    currentPrice = currentPrice,
                    maxPrice = maxPrice,
                    bidItemDialogListener = object : BidItemDialogListener {
                        override fun onBidItemClick(price: Long) {
                            viewModel.bidItem(
                                itemId = itemId,
                                price = price,
                                maxPrice = maxPrice,
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
    }

    private fun hideDialog() {
        bidItemDialog.hide()
        viewModel.fetchItemDetails(itemId = itemId)
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
                    hideDialog()
                    requireContext().showToast("성공적으로 입찰되었습니다")
                }

                is ItemDetailsSideEffect.BidSuccessful -> {
                    hideDialog()
                    requireContext().showToast("성공적으로 낙찰되었습니다!")
                    navController.popBackStack()
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
