package com.example.snapcart_android_app.ui.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.GetProductByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductDetailUIState>(ProductDetailUIState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getProductById(id: Long) {
        viewModelScope.launch {
            _uiState.value = ProductDetailUIState.Loading
            when (val result = getProductByIdUseCase.execute(id)) {
                is ResultWrapper.Success -> {
                    _uiState.value = ProductDetailUIState.Success(result.value)
                }
                is ResultWrapper.Failure -> {
                    _uiState.value = ProductDetailUIState.Error("Failed to load product")
                }
            }
        }
    }
}

sealed class ProductDetailUIState {
    object Loading : ProductDetailUIState()
    data class Success(val product: Product) : ProductDetailUIState()
    data class Error(val message: String) : ProductDetailUIState()
}