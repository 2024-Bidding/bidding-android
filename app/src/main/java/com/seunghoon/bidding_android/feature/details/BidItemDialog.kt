package com.seunghoon.bidding_android.feature.details

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.seunghoon.bidding_android.databinding.DialogBidItemBinding

class BidItemDialog(context: Context) : Dialog(context) {

    private val binding by lazy {
        DialogBidItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
