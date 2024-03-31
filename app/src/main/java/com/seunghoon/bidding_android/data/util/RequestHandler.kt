package com.seunghoon.bidding_android.data.util

import io.ktor.client.plugins.ClientRequestException

internal class RequestHandler<T> {
    internal suspend fun request(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: ClientRequestException) {
            when (e.response.status.value) {
                401 -> throw Unauthorized
                404 -> throw NotFound
                else -> throw e
            }
        }
    }
}
