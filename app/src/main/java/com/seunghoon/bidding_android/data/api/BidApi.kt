package com.seunghoon.bidding_android.data.api

import com.seunghoon.bidding_android.data.di.ktorClient
import com.seunghoon.bidding_android.data.model.item.request.BidItemRequest
import com.seunghoon.bidding_android.data.util.LocalStorage
import com.seunghoon.bidding_android.data.util.RequestHandler
import com.seunghoon.bidding_android.data.util.RequestUrl
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.client.request.url

internal class BidApi(
    private val localStorage: LocalStorage,
) {
    suspend fun bidItem(
        itemId: Long,
        bidItemRequest: BidItemRequest,
    ) = runCatching {
        RequestHandler<Unit>().request {
            ktorClient.patch {
                url(RequestUrl.Bid.bidItem(itemId))
                setBody(bidItemRequest)
                header(
                    key = "Authorization",
                    value = localStorage.getValue(LocalStorage.ACCESS_TOKEN)
                )
            }
        }
    }
}
