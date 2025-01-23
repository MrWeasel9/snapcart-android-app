package com.example.snapcart_android_app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetOrdersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(private val getOrdersUseCase: GetOrdersUseCase) : ViewModel() {
    private val _ordersState = MutableStateFlow<OrdersState>(OrdersState.Loading)
    val ordersState: StateFlow<OrdersState> = _ordersState

    fun loadOrders(userId: String) {
        viewModelScope.launch {
            _ordersState.value = OrdersState.Loading
            when (val result = getOrdersUseCase.execute(userId)) {
                is com.example.domain.network.ResultWrapper.Success -> {
                    _ordersState.value = OrdersState.Success(result.value)
                }
                is com.example.domain.network.ResultWrapper.Failure -> {
                    _ordersState.value = OrdersState.Error("Failed to load orders")
                }
            }
        }
    }

    sealed class OrdersState {
        data object Loading : OrdersState()
        data class Success(val orders: List<com.example.domain.model.Order>) : OrdersState()
        data class Error(val message: String) : OrdersState()
    }
}