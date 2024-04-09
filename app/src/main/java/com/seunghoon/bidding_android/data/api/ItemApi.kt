package com.seunghoon.bidding_android.data.api

import com.seunghoon.bidding_android.data.di.ktorClient
import com.seunghoon.bidding_android.data.model.ItemsResponse
import com.seunghoon.bidding_android.data.util.RequestHandler
import com.seunghoon.bidding_android.data.util.RequestUrl
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

internal class ItemApi {
    suspend fun fetchItems() = runCatching {
        RequestHandler<ItemsResponse>().request {
            ktorClient.get {
                url(RequestUrl.Items.path)
            }.body<ItemsResponse>()
        }
    }
}
