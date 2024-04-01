package com.seunghoon.bidding_android.feature.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal abstract class BaseViewModel<T, E>(
    private val initialState: T,
) : ViewModel() {
    private val _state: MutableStateFlow<T> = MutableStateFlow(initialState)
    val state: StateFlow<T> = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<E> = MutableSharedFlow()
    val sideEffect: SharedFlow<E> = _sideEffect.asSharedFlow()

    protected fun updateState(state: T) {
        _state.value = state
    }

    protected fun postSideEffect(sideEffect: E) {
        viewModelScope.launch {
            _sideEffect.emit(sideEffect)
        }
    }

    fun collectSideEffect(block: (E) -> Unit) {
        viewModelScope.launch {
            _sideEffect.collect {
                block(it)
            }
        }
    }
}
