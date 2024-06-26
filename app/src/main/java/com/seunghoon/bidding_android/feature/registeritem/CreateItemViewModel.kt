package com.seunghoon.bidding_android.feature.registeritem

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.seunghoon.bidding_android.data.api.FileApi
import com.seunghoon.bidding_android.data.api.ItemApi
import com.seunghoon.bidding_android.data.model.file.request.CreatePresignedUrlRequest
import com.seunghoon.bidding_android.data.model.item.request.CreateItemRequest
import com.seunghoon.bidding_android.data.util.BadRequest
import com.seunghoon.bidding_android.data.util.FileUtil
import com.seunghoon.bidding_android.data.util.Unauthorized
import com.seunghoon.bidding_android.feature.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime

internal class CreateItemViewModel(
    private val itemApi: ItemApi,
    private val fileApi: FileApi,
) : BaseViewModel<RegisterItemState, RegisterItemSideEffect>(RegisterItemState.getDefaultState()) {

    internal val uris: MutableList<Uri> = mutableListOf()
    private val files: MutableList<File> = mutableListOf()

    fun createItem(
        name: String,
        endPrice: String,
        startPrice: String,
        startDate: String,
        endDate: String,
        startTime: String,
        endTime: String,
        imageUrls: List<String>,
        content: String,
    ) {
        if (imageUrls.isEmpty()) {
            postSideEffect(RegisterItemSideEffect.Failure(message = "이미지를 첨부해주세요"))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                itemApi.createItem(
                    createItemRequest = CreateItemRequest(
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
                        content = content,
                    )
                ).onSuccess {
                    postSideEffect(RegisterItemSideEffect.Success)
                }.onFailure {
                    when (it) {
                        is Unauthorized -> {
                            postSideEffect(RegisterItemSideEffect.Failure("토큰이 만료되었어요"))
                        }

                        is BadRequest -> {
                            postSideEffect(RegisterItemSideEffect.Failure("잘못된 형식의 요청이에요"))
                        }

                        else -> {
                            postSideEffect(RegisterItemSideEffect.Failure(it.message.toString()))
                        }
                    }
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
                ).onFailure {
                    postSideEffect(RegisterItemSideEffect.Failure(message = "파일 업로드 중 문제가 발생했습니다"))
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
