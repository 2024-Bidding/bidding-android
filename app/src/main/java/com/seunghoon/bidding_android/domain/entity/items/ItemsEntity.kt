package com.seunghoon.bidding_android.domain.entity.items

import com.seunghoon.bidding_android.data.enums.BiddingStatus
import com.seunghoon.bidding_android.data.model.item.response.ItemsResponse
import java.text.DecimalFormat

data class ItemsEntity(
    val items: List<ItemEntity>,
) {
    data class ItemEntity(
        val id: Long,
        val name: String,
        val imageUrl: String,
        val endTime: String,
        val currentPrice: String,
        val userName: String,
        val userProfileImageUrl: String,
    )
}

internal fun ItemsResponse.toEntity() = ItemsEntity(
    items = items.map { it.toEntity() },
)

private fun ItemsResponse.Item.toEntity() = ItemsEntity.ItemEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    endTime = endTime.split("T")[0],
    currentPrice = DecimalFormat("#,###").format(currentPrice),
    userName = userName,
    userProfileImageUrl = userProfileUrl,
)
