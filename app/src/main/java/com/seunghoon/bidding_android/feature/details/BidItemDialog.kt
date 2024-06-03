package com.seunghoon.bidding_android.feature.details

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.seunghoon.bidding_android.common.formatted
import com.seunghoon.bidding_android.databinding.DialogBidItemBinding

internal class BidItemDialog(
    context: Context,
    private val currentPrice: Long,
    private val maxPrice: Long,
    private val bidItemDialogListener: BidItemDialogListener,
) : Dialog(context) {

    private val binding by lazy {
        DialogBidItemBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvBidItemMaxPrice.text = "${currentPrice.formatted()} ~ ${
            maxPrice.formatted()
        }원까지 입력 가능"
        binding.btnBidItemBid.setOnClickListener {
            bidItemDialogListener.onBidItemClick(binding.etBidItemPrice.text.toString().toLong())
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding.unbind()
    }
}

interface BidItemDialogListener {
    fun onBidItemClick(price: Long)
}
