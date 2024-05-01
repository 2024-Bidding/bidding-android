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
        val startPrice: Long,
        val endPrice: Long,
        val imageUrl: String,
        val startTime: String,
        val endTime: String,
        val currentPrice: String,
        val biddingStatus: BiddingStatus,
    )
}

internal fun ItemsResponse.toEntity() = ItemsEntity(
    items = items.map { it.toEntity() },
)

private fun ItemsResponse.Item.toEntity() = ItemsEntity.ItemEntity(
    id = id,
    name = name,
    startPrice = startPrice,
    endPrice = endPrice,
    imageUrl = imageUrl,
    startTime = startTime.split("T")[0] + " ~ ",
    endTime = endTime.split("T")[0],
    currentPrice = DecimalFormat("#,###").format(currentPrice),
    biddingStatus = biddingStatus,
)
