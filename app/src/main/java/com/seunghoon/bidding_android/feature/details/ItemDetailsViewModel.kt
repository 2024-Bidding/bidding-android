package com.seunghoon.bidding_android.feature.details

import androidx.lifecycle.viewModelScope
import com.seunghoon.bidding_android.data.api.ItemApi
import com.seunghoon.bidding_android.data.model.item.request.BidItemRequest
import com.seunghoon.bidding_android.data.model.item.response.ItemDetailsResponse
import com.seunghoon.bidding_android.data.util.BadRequest
import com.seunghoon.bidding_android.feature.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class ItemDetailsViewModel(
    private val itemApi: ItemApi,
) : BaseViewModel<Unit, ItemDetailsSideEffect>(Unit) {

    internal fun fetchItemDetails(itemId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            itemApi.fetchItemDetails(itemId).onSuccess {
                postSideEffect(ItemDetailsSideEffect.Success(it))
            }
        }
    }

    internal fun bidItem(
        itemId: Long,
        price: Long,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            itemApi.bidItem(
                itemId = itemId,
                bidItemRequest = BidItemRequest(
                    price = price,
                )
            ).onSuccess {
                postSideEffect(ItemDetailsSideEffect.BidSuccess)
            }.onFailure {
                when (it) {
                    is BadRequest -> {
                        postSideEffect(ItemDetailsSideEffect.Failure("입찰가 범위가 잘못되었어요"))
                    }
                }
            }
        }
    }
}

internal sealed interface ItemDetailsSideEffect {
    data class Success(val details: ItemDetailsResponse) : ItemDetailsSideEffect
    data object BidSuccess : ItemDetailsSideEffect
    data class Failure(val message: String) : ItemDetailsSideEffect
}
