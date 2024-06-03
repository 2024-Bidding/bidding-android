package com.seunghoon.bidding_android.feature.details

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.icu.text.DecimalFormat
import android.os.Bundle
import com.seunghoon.bidding_android.databinding.DialogBidItemBinding

class BidItemDialog(
    context: Context,
    private val maxPrice: Long,
) : Dialog(context) {

    private val binding by lazy {
        DialogBidItemBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvBidItemMaxPrice.text = "최대 ${DecimalFormat("#,###").format(maxPrice)}원까지 입력 가능"
    }
}
