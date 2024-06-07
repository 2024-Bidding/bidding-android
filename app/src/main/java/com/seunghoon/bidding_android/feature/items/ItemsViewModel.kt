package com.seunghoon.bidding_android.feature.items

import androidx.lifecycle.viewModelScope
import com.seunghoon.bidding_android.data.api.ItemApi
import com.seunghoon.bidding_android.domain.entity.items.ItemsEntity
import com.seunghoon.bidding_android.domain.entity.items.toEntity
import com.seunghoon.bidding_android.feature.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class ItemsViewModel(
    private val itemApi: ItemApi,
) : BaseViewModel<Unit, ItemsSideEffect>(initialState = Unit) {

    fun fetchItems() {
        viewModelScope.launch(Dispatchers.IO) {
            itemApi.fetchItems().onSuccess {
                postSideEffect(ItemsSideEffect.Success(it.toEntity().items))
            }
        }
    }

    fun likeItem(itemId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            itemApi.likeItem(itemId = itemId).onSuccess {

            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun likes() {
        viewModelScope.launch(Dispatchers.IO) {
            itemApi.fetchLikeItems().onSuccess {
                postSideEffect(ItemsSideEffect.Success(items = it.toEntity().items))
            }.onFailure {
                postSideEffect(ItemsSideEffect.Failure(message = it.toString()))
            }
        }
    }
}

internal sealed interface ItemsSideEffect {
    data class Success(val items: List<ItemsEntity.ItemEntity>) : ItemsSideEffect
    data class Failure(val message: String) : ItemsSideEffect
}
