package com.seunghoon.bidding_android.data.api

import com.seunghoon.bidding_android.data.di.ktorClient
import com.seunghoon.bidding_android.data.model.item.request.RegisterItemRequest
import com.seunghoon.bidding_android.data.model.item.response.ItemsResponse
import com.seunghoon.bidding_android.data.util.LocalStorage
import com.seunghoon.bidding_android.data.util.RequestHandler
import com.seunghoon.bidding_android.data.util.RequestUrl
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
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

    suspend fun registerItem(registerItemRequest: RegisterItemRequest) = runCatching {
        RequestHandler<Unit>().request {
            ktorClient.post {
                url(RequestUrl.Items.path)
                setBody(registerItemRequest)
                header(
                    key = "Authorization",
                    value = localStorage.getValue(LocalStorage.ACCESS_TOKEN),
                )
            }
        }
    }
}
