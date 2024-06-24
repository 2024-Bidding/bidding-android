package com.seunghoon.bidding_android.feature.search

import androidx.lifecycle.viewModelScope
import com.seunghoon.bidding_android.data.api.ItemApi
import com.seunghoon.bidding_android.domain.entity.items.ItemsEntity
import com.seunghoon.bidding_android.domain.entity.items.toEntity
import com.seunghoon.bidding_android.feature.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

internal class SearchViewModel(
    private val itemApi: ItemApi,
) : BaseViewModel<Unit, SearchSideEffect>(Unit) {
    private val keyword: MutableSharedFlow<String> = MutableSharedFlow()

    private val _items: MutableList<ItemsEntity.ItemEntity> = mutableListOf()
    val items: MutableList<ItemsEntity.ItemEntity> = mutableListOf()

    init {
        collectKeyword()
        fetchItems()
    }

    internal fun updateKeyword(keyword: String?) {
        viewModelScope.launch {
            if (!keyword.isNullOrBlank()) {
                this@SearchViewModel.keyword.emit(keyword)
            }
        }
    }

    private fun fetchItems() {
        viewModelScope.launch(Dispatchers.IO) {
            itemApi.fetchItems().onSuccess {
                _items.addAll(it.toEntity().items)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun collectKeyword() {
        viewModelScope.launch {
            keyword.debounce(1000L).collect { keyword ->
                items.clear()
                items.addAll(_items.filter { item ->
                    item.name.contains(keyword)
                }
                )
                postSideEffect(SearchSideEffect.Success(items))
            }
        }
    }
}

internal sealed interface SearchSideEffect {
    data class Success(val items: List<ItemsEntity.ItemEntity>) : SearchSideEffect
}
