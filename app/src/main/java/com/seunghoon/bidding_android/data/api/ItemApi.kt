package com.seunghoon.bidding_android.data.api

import com.seunghoon.bidding_android.data.di.ktorClient
import com.seunghoon.bidding_android.data.model.item.request.BidItemRequest
import com.seunghoon.bidding_android.data.model.item.request.CreateItemRequest
import com.seunghoon.bidding_android.data.model.item.response.ItemDetailsResponse
import com.seunghoon.bidding_android.data.model.item.response.ItemsResponse
import com.seunghoon.bidding_android.data.util.LocalStorage
import com.seunghoon.bidding_android.data.util.RequestHandler
import com.seunghoon.bidding_android.data.util.RequestUrl
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

internal class ItemApi(
    private val localStorage: LocalStorage,
) {
    suspend fun fetchItems() = runCatching {
        RequestHandler<ItemsResponse>().request {
            ktorClient.get {
                url(RequestUrl.Items.path)
            }.body<ItemsResponse>()
        }
    }

    suspend fun createItem(createItemRequest: CreateItemRequest) = runCatching {
        RequestHandler<Unit>().request {
            ktorClient.post {
                url(RequestUrl.Items.path)
                setBody(createItemRequest)
                header(
                    key = "Authorization",
                    value = localStorage.getValue(LocalStorage.ACCESS_TOKEN),
                )
            }
        }
    }

    suspend fun fetchItemDetails(itemId: Long) = runCatching {
        RequestHandler<ItemDetailsResponse>().request {
            ktorClient.get {
                url("${RequestUrl.Items.path}/${itemId}")
            }.body<ItemDetailsResponse>()
        }
    }

    suspend fun bidItem(
        itemId: Long,
        bidItemRequest: BidItemRequest,
    ) = runCatching {
        RequestHandler<Unit>().request {
            ktorClient.patch {
                url("${RequestUrl.Items.bid}/${itemId}")
                setBody(bidItemRequest)
                header(
                    key = "Authorization",
                    value = localStorage.getValue(LocalStorage.ACCESS_TOKEN)
                )
            }
        }
    }
}
