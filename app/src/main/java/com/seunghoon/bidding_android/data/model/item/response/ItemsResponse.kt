package com.seunghoon.bidding_android.data.model.item.response

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
        @SerialName("image_url") val imageUrl: String,
        @SerialName("end_time") val endTime: String,
        @SerialName("current_price") val currentPrice: Long,
        @SerialName("user_name") val userName: String,
        @SerialName("user_profile_url") val userProfileUrl: String,
    )
}
