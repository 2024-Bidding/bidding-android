package com.seunghoon.bidding_android.data.model.item.response

import com.seunghoon.bidding_android.data.enums.BiddingStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemDetailsResponse(
    @SerialName("name") val name: String,
    @SerialName("current_price") val currentPrice: Long,
    @SerialName("max_price") val maxPrice: Long,
    @SerialName("image_urls") val imageUrls: List<String>,
    @SerialName("start_time") val startTime: String,
    @SerialName("end_time") val endTime: String,
    @SerialName("bidding_status") val biddingStatus: BiddingStatus,
    @SerialName("user_name") val userName: String,
    @SerialName("content") val content: String,
)
