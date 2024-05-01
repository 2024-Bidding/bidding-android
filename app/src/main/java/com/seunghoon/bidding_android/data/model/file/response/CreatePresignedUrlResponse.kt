package com.seunghoon.bidding_android.data.model.file.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePresignedUrlResponse(
    @SerialName("urls") val urls: List<UrlResponse>
) {
    @Serializable
    data class UrlResponse(
        @SerialName("file_path") val filePath: String,
        @SerialName("pre_signed_url") val preSignedUrl: String,
    )
}
