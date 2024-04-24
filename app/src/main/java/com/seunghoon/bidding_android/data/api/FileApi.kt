package com.seunghoon.bidding_android.data.api

import com.seunghoon.bidding_android.data.di.ktorFileClient
import com.seunghoon.bidding_android.data.model.file.request.CreatePresignedUrlRequest
import com.seunghoon.bidding_android.data.model.file.response.CreatePresignedUrlResponse
import com.seunghoon.bidding_android.data.util.RequestHandler
import com.seunghoon.bidding_android.data.util.RequestUrl
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.content.PartData
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

    /*suspend fun uploadFile(
        presignedUrl: String,
        file: File,
    ) = runCatching {
        RequestHandler<Unit>().request {
            ktorFileClient.submitFormWithBinaryData(
                url = presignedUrl,
                formData = PartData
            )
        }
    }*/
}
