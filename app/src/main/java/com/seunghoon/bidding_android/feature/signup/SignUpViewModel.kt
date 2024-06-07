package com.seunghoon.bidding_android.feature.signup

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.seunghoon.bidding_android.data.api.FileApi
import com.seunghoon.bidding_android.data.api.UserApi
import com.seunghoon.bidding_android.data.model.file.request.CreatePresignedUrlRequest
import com.seunghoon.bidding_android.data.model.user.request.SignUpRequest
import com.seunghoon.bidding_android.data.util.Conflict
import com.seunghoon.bidding_android.data.util.FileUtil
import com.seunghoon.bidding_android.feature.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class SignUpViewModel(
    private val userApi: UserApi,
    private val fileApi: FileApi,
) : BaseViewModel<Unit, SignUpSideEffect>(Unit) {

    fun signUp(
        email: String,
        name: String,
        password: String,
        profileImageUrl: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userApi.signUp(
                    SignUpRequest(
                        email = email,
                        password = password,
                        name = name,
                        profileImageUrl = profileImageUrl,
                    )
                )
            }.onSuccess {
                postSideEffect(SignUpSideEffect.Success)
            }.onFailure {
                when (it) {
                    is Conflict -> {
                        postSideEffect(SignUpSideEffect.EmailAlreadyExists)
                    }
                }
            }
        }
    }

    fun uploadImage(
        uri: Uri,
        context: Context,
    ) {
        val file = FileUtil.toFile(
            context = context,
            uri = uri,
        )
        viewModelScope.launch(Dispatchers.IO) {
            fileApi.createPresignedUrl(
                createPresignedUrl = CreatePresignedUrlRequest(
                    files = listOf(file).map {
                        CreatePresignedUrlRequest.FileRequest(
                            fileName = it.name,
                        )
                    }
                )
            ).onSuccess {
                val response = it.urls.first()
                fileApi.uploadFile(
                    presignedUrl = response.preSignedUrl,
                    file = file,
                ).onSuccess {
                    postSideEffect(SignUpSideEffect.SuccessFileUpload(profileImageUrl = response.filePath))
                }.onFailure {
                    it.printStackTrace()
                }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}

internal sealed interface SignUpSideEffect {
    data object Success : SignUpSideEffect
    data class SuccessFileUpload(val profileImageUrl: String) : SignUpSideEffect
    data object EmailAlreadyExists : SignUpSideEffect
}
