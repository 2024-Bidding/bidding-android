package com.seunghoon.bidding_android.feature.mypage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.seunghoon.bidding_android.data.api.BidApi
import com.seunghoon.bidding_android.data.api.ItemApi
import com.seunghoon.bidding_android.data.api.UserApi
import com.seunghoon.bidding_android.data.model.user.response.UserResponse
import com.seunghoon.bidding_android.domain.entity.items.ItemsEntity
import com.seunghoon.bidding_android.domain.entity.items.toEntity
import com.seunghoon.bidding_android.feature.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class MyPageViewModel(
    private val userApi: UserApi,
    private val bidApi: BidApi,
    private val itemApi: ItemApi,
) : BaseViewModel<Unit, MyPageSideEffect>(Unit) {

    internal fun fetchUserInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            userApi.fetchUserInformation().onSuccess {
                postSideEffect(MyPageSideEffect.Success(it))
            }.onFailure {
                Log.d("TEST", it.toString())
            }
        }
    }

    internal fun fetchMyBidItems() {
        viewModelScope.launch(Dispatchers.IO) {
            bidApi.fetchMyBidItems().onSuccess {
                postSideEffect(MyPageSideEffect.SuccessFetchMyBidItems(items = it.toEntity()))
            }.onFailure {
                Log.d("TEST", it.toString())
            }
        }
    }

    internal fun fetchMyItems() {
        viewModelScope.launch(Dispatchers.IO) {
            itemApi.fetchMyItems().onSuccess {
                postSideEffect(MyPageSideEffect.SuccessFetchMyItems(items = it.toEntity()))
            }.onFailure {
                Log.d("TEST", it.toString())
            }
        }
    }
}

sealed interface MyPageSideEffect {
    data class Success(val response: UserResponse) : MyPageSideEffect
    data class SuccessFetchMyBidItems(val items: ItemsEntity) : MyPageSideEffect
    data class SuccessFetchMyItems(val items: ItemsEntity) : MyPageSideEffect
}
