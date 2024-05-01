package com.seunghoon.bidding_android.data.model.item.response

import com.seunghoon.bidding_android.data.enums.BiddingStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemsResponse(
    @SerialName("items") val items: List<Item>,
) {
    @Serializable
    data class Item(
        @SerialName("id") val id: Long,
        @SerialName("name") val name: String,
        @SerialName("start_price") val startPrice: Long,
        @SerialName("end_price") val endPrice: Long,
        @SerialName("image_url") val imageUrl: String,
        @SerialName("start_time") val startTime: String,
        @SerialName("end_time") val endTime: String,
        @SerialName("current_price") val currentPrice: Long,
        @SerialName("bidding_status") val biddingStatus: BiddingStatus,
    )
}
