// HomeViewModelTest.kt
package com.example.snapcart_android_app.ui.feature.home

import app.cash.turbine.test
import com.example.domain.model.Product
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.GetCategoriesUseCase
import com.example.domain.usecase.GetProductUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getProductUseCase: GetProductUseCase = mockk()
    private val getCategoriesUseCase: GetCategoriesUseCase = mockk()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        // Mock dependencies
        coEvery { getProductUseCase.execute(any()) } returns ResultWrapper.Success(emptyList())
        coEvery { getCategoriesUseCase.execute() } returns ResultWrapper.Success(emptyList())

        // Initialize ViewModel AFTER mocking
        viewModel = HomeViewModel(getProductUseCase, getCategoriesUseCase)

        viewModel.uiState.test {
            assertEquals(HomeScreenUIEvents.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents() // Prevent timeout
        }
    }

    @Test
    fun `when data fetch succeeds, emit Success state`() = runTest {
        // Mock successful responses
        val mockFeatured = listOf(Product(1, "Laptop", 999.99, "electronics", "", ""))
        val mockPopular = listOf(Product(2, "Ring", 299.99, "jewelery", "", ""))
        val mockCategories = listOf("electronics", "jewelery")

        coEvery { getProductUseCase.execute("electronics") } returns ResultWrapper.Success(mockFeatured)
        coEvery { getProductUseCase.execute("jewelery") } returns ResultWrapper.Success(mockPopular)
        coEvery { getCategoriesUseCase.execute() } returns ResultWrapper.Success(mockCategories)

        // Initialize ViewModel AFTER mocking
        viewModel = HomeViewModel(getProductUseCase, getCategoriesUseCase)

        viewModel.uiState.test {
            assertTrue(awaitItem() is HomeScreenUIEvents.Loading)
            val successState = awaitItem() as HomeScreenUIEvents.Success
            assertEquals(mockFeatured, successState.featured)
            assertEquals(mockPopular, successState.popular)
            assertEquals(mockCategories, successState.categories)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when all data fetches fail, emit Error state`() = runTest {
        // Mock empty responses
        coEvery { getProductUseCase.execute(any()) } returns ResultWrapper.Success(emptyList())
        coEvery { getCategoriesUseCase.execute() } returns ResultWrapper.Success(emptyList())

        // Initialize ViewModel AFTER mocking
        viewModel = HomeViewModel(getProductUseCase, getCategoriesUseCase)

        viewModel.uiState.test {
            assertTrue(awaitItem() is HomeScreenUIEvents.Loading)
            assertTrue(awaitItem() is HomeScreenUIEvents.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `verify use cases are called with correct parameters`() = runTest {
        coEvery { getProductUseCase.execute("electronics") } returns ResultWrapper.Success(emptyList())
        coEvery { getProductUseCase.execute("jewelery") } returns ResultWrapper.Success(emptyList())
        coEvery { getCategoriesUseCase.execute() } returns ResultWrapper.Success(emptyList())

        // Initialize ViewModel AFTER mocking
        viewModel = HomeViewModel(getProductUseCase, getCategoriesUseCase)

        viewModel.uiState.test {
            awaitItem() // Loading
            awaitItem() // Final state
        }

        coVerify {
            getProductUseCase.execute("electronics")
            getProductUseCase.execute("jewelery")
            getCategoriesUseCase.execute()
        }
    }
}