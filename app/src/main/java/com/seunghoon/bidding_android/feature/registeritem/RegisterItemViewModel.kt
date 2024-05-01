package com.seunghoon.bidding_android.feature.registeritem

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.seunghoon.bidding_android.data.api.FileApi
import com.seunghoon.bidding_android.data.api.ItemApi
import com.seunghoon.bidding_android.data.model.file.request.CreatePresignedUrlRequest
import com.seunghoon.bidding_android.data.model.item.request.RegisterItemRequest
import com.seunghoon.bidding_android.data.util.FileUtil
import com.seunghoon.bidding_android.feature.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime

internal class RegisterItemViewModel(
    private val itemApi: ItemApi,
    private val fileApi: FileApi,
) : BaseViewModel<RegisterItemState, RegisterItemSideEffect>(RegisterItemState.getDefaultState()) {

    private val uris: MutableList<Uri> = mutableListOf()
    private val files: MutableList<File> = mutableListOf()

    fun registerItem(
        name: String,
        endPrice: String,
        startPrice: String,
        startDate: String,
        endDate: String,
        startTime: String,
        endTime: String,
        imageUrls: List<String>
    ) {
        if (name.isBlank() || endPrice.isBlank() || startPrice.isBlank() || startDate.isBlank() || endDate.isBlank() || startTime.isBlank() || endTime.isBlank()) {
            postSideEffect(RegisterItemSideEffect.Failure(message = "모든 값을 입력해주세요"))
            return
        }
        if (imageUrls.isEmpty()) {
            postSideEffect(RegisterItemSideEffect.Failure(message = "이미지를 첨부해주세요"))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                itemApi.registerItem(
                    registerItemRequest = RegisterItemRequest(
                        name = name,
                        endPrice = endPrice.toLong(),
                        startPrice = startPrice.toLong(),
                        imageUrls = imageUrls,
                        startTime = makeLocalDateTime(
                            date = startDate,
                            time = startTime,
                        ),
                        endTime = makeLocalDateTime(
                            date = endDate,
                            time = endTime,
                        ),
                    )
                ).onSuccess {
                    postSideEffect(RegisterItemSideEffect.Success)
                }.onFailure {
                    Log.d("TEST", it.toString())
                    postSideEffect(RegisterItemSideEffect.Failure(it.toString()))
                }
            }
        }
    }

    fun createPresignedUrl() {
        viewModelScope.launch(Dispatchers.IO) {
            fileApi.createPresignedUrl(
                createPresignedUrl = CreatePresignedUrlRequest(
                    files = files.map { file ->
                        CreatePresignedUrlRequest.FileRequest(
                            fileName = file.name,
                        )
                    }
                )
            ).onSuccess {
                postSideEffect(
                    RegisterItemSideEffect.SuccessCreatePresignedUrl(
                        filePath = it.urls.map { it.filePath },
                        presignedUrls = it.urls.map { it.preSignedUrl },
                    )
                )
            }.onFailure {

            }
        }
    }

    fun uploadFile(presignedUrls: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            presignedUrls.forEachIndexed { index, s ->
                fileApi.uploadFile(
                    presignedUrl = s,
                    file = files[index],
                ).onSuccess {
                    cancel()
                }.onFailure {
                    Log.d("TEST", it.toString())
                }
            }
        }
    }

    internal fun addUri(
        uri: Uri,
        context: Context,
    ) {
        uris.add(uri)
        files.add(
            FileUtil.toFile(
                context = context,
                uri = uri,
            )
        )
    }

    internal fun removeUri(index: Int) {
        uris.removeAt(index)
        files.removeAt(index)
    }

    private fun makeLocalDateTime(
        date: String,
        time: String,
    ): String = date.split(".").run {
        val year = get(0)
        val month = get(1).padStart(2, '0')
        val day = get(2).padStart(2, '0')

        val hour = time.split(":")[0].padStart(2, '0')
        val minute = time.split(":")[1].padStart(2, '0')

        "$year-$month-${day}T$hour:$minute:00.000000"
    }
}

internal data class RegisterItemState(
    val imageUrl: String,
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val endTime: String,
    val imageUrls: List<String>,
) {
    companion object {
        fun getDefaultState() = RegisterItemState(
            imageUrl = "",
            startDate = LocalDate.now().toString(),
            endDate = LocalDate.now().toString(),
            startTime = LocalDateTime.now().toString(),
            endTime = LocalDateTime.now().toString(),
            imageUrls = emptyList(),
        )
    }
}

internal sealed interface RegisterItemSideEffect {
    data object Success : RegisterItemSideEffect
    data class Failure(val message: String) : RegisterItemSideEffect
    data class SuccessCreatePresignedUrl(
        val filePath: List<String>,
        val presignedUrls: List<String>,
    ) : RegisterItemSideEffect
}
