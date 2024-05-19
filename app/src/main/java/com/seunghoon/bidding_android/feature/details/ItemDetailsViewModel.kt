package com.seunghoon.bidding_android.feature.details

import androidx.lifecycle.viewModelScope
import com.seunghoon.bidding_android.data.api.ItemApi
import com.seunghoon.bidding_android.data.model.item.response.ItemDetailsResponse
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
}

internal sealed interface ItemDetailsSideEffect {
    data class Success(val details: ItemDetailsResponse) : ItemDetailsSideEffect
    data class Failure(val message: String) : ItemDetailsSideEffect
}
