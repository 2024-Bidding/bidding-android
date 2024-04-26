package com.seunghoon.bidding_android.data.api

import com.seunghoon.bidding_android.data.di.ktorFileClient
import com.seunghoon.bidding_android.data.di.ktorS3Client
import com.seunghoon.bidding_android.data.model.file.request.CreatePresignedUrlRequest
import com.seunghoon.bidding_android.data.model.file.response.CreatePresignedUrlResponse
import com.seunghoon.bidding_android.data.util.RequestHandler
import com.seunghoon.bidding_android.data.util.RequestUrl
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.util.cio.readChannel
import java.io.File

internal class FileApi {
    suspend fun createPresignedUrl(createPresignedUrl: CreatePresignedUrlRequest) = runCatching {
        RequestHandler<CreatePresignedUrlResponse>().request {
            ktorFileClient.post {
                url(RequestUrl.File.presignedUrl)
                setBody(createPresignedUrl)
            }.body<CreatePresignedUrlResponse>()
        }
    }

    suspend fun uploadFile(
        presignedUrl: String,
        file: File,
    ) = runCatching {
        RequestHandler<Unit>().request {
            ktorS3Client.put {
                url(presignedUrl)
                contentType(ContentType.MultiPart.FormData)
                header(HttpHeaders.ContentLength, file.length())
                setBody(file.readChannel())
            }
        }
    }
}
