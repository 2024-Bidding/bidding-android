package com.seunghoon.bidding_android.data.model.item.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BidItemRequest(
    @SerialName("price") val price: Long,
)
