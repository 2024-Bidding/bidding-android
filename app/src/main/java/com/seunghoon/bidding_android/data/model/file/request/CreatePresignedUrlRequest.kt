package com.seunghoon.bidding_android.data.model.file.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePresignedUrlRequest(
    @SerialName("files") val files: List<FileRequest>,
) {
    @Serializable
    data class FileRequest(
        @SerialName("type") val type: String = "EXTENSION_FILE",
        @SerialName("file_name") val fileName: String,
    )
}
