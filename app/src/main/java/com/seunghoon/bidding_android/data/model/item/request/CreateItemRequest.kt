package com.seunghoon.bidding_android.data.model.item.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateItemRequest(
    @SerialName("name") val name: String,
    @SerialName("end_price") val endPrice: Long,
    @SerialName("start_price") val startPrice: Long,
    @SerialName("image_urls") val imageUrls: List<String>,
    @SerialName("start_time") val startTime: String,
    @SerialName("end_time") val endTime: String,
    @SerialName("content") val content: String,
)
