package com.example.snapcart_android_app.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.GetCategoriesUseCase
import com.example.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val getProductUseCase: GetProductUseCase,
    private val categoryUseCase: GetCategoriesUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUIEvents>(HomeScreenUIEvents.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _uiState.value = HomeScreenUIEvents.Loading
            val featured = getProducts("electronics")
            val popular = getProducts("jewelery")
            val categories = getCategory()
            if (featured.isEmpty() && popular.isEmpty() && categories.isEmpty()) {
                _uiState.value = HomeScreenUIEvents.Error("Failed to fetch products")
                return@launch
            }
            _uiState.value = HomeScreenUIEvents.Success(featured, popular, categories)
        }
    }
    private suspend fun getCategory() : List<String> {
        categoryUseCase.execute().let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    return (result).value
                }

                is ResultWrapper.Failure -> {
                    return emptyList()
                }

            }
        }
    }

    private suspend fun getProducts(category: String?) : List<Product> {

        getProductUseCase.execute(category).let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    return (result).value
                }

                is ResultWrapper.Failure -> {
                    return emptyList()
                }

            }
        }
    }
}

sealed class HomeScreenUIEvents {
    data object Loading : HomeScreenUIEvents()
    data class Success(val featured: List<Product>, val popular:List<Product>,
                       val categories : List<String>) : HomeScreenUIEvents()
    data class Error(val message: String) : HomeScreenUIEvents()
}