package com.example.emarketcaseapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.emarketcaseapp.data.local.CartProduct
import com.example.emarketcaseapp.data.repository.CartRepository
import com.example.emarketcaseapp.domain.model.CartModel
import com.example.emarketcaseapp.domain.model.Product
import com.example.emarketcaseapp.domain.use_case.get_produckts.GetProductsUseCase
import com.example.emarketcaseapp.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CartScreenViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var cartRepository: CartRepository

    private lateinit var viewModel: CartScreenViewModel

    @Before
    fun setUp() {
        getProductsUseCase = mockk()
        cartRepository = mockk()

        viewModel = CartScreenViewModel(
            getProductsUseCase,
            cartRepository
        )
    }

    @Test
    fun `getProducts should update products when successful`() = runTest {
        val products = listOf(
            Product(brand = "Brand A", createdAt = "2022-01-01", description = "Description A", id = "1", image = "imageA", model = "Model A", name = "Product 1", price = "10.0"),
            Product(brand = "Brand B", createdAt = "2022-01-02", description = "Description B", id = "2", image = "imageB", model = "Model B", name = "Product 2", price = "20.0")
        )
        coEvery { getProductsUseCase.executeGetProducts() } returns flowOf(Resource.Success(products))

        viewModel.getProducts()

        assertEquals(products, viewModel.products.value)
    }

    @Test
    fun `getProducts should update errorMessage when there is an error`() = runTest {
        val errorMessage = "An error occurred"
        coEvery { getProductsUseCase.executeGetProducts() } returns flowOf(Resource.Error(errorMessage))

        viewModel.getProducts()

        assertEquals(errorMessage, viewModel.errorMessage.value)
    }

    @Test
    fun `loadCartProducts should update cartProducts from repository`() = runTest {
        val cartProducts = listOf(
            CartProduct(productId = "1", quantity = 2),
            CartProduct(productId = "2", quantity = 1)
        )
        coEvery { cartRepository.getAllCartProducts() } returns cartProducts

        viewModel.loadCartProducts()

        assertEquals(cartProducts, viewModel.cartProducts.value)
    }

    @Test
    fun `updateCartProduct should call cartRepository to update cart product`() = runTest {
        val productId = "1"
        coEvery { cartRepository.updateCartProduct(productId, true) } just runs

        viewModel.updateCartProduct(productId, true)

        coVerify { cartRepository.updateCartProduct(productId, true) }
    }

    @Test
    fun `calculateTotalValue should calculate total correctly`() = runTest {
        val cartModelList = listOf(
            CartModel(product = Product(brand = "Brand A", createdAt = "2022-01-01", description = "Description A", id = "1", image = "imageA", model = "Model A", name = "Product 1", price = "10.0"), piece = "2"),
            CartModel(product = Product(brand = "Brand B", createdAt = "2022-01-02", description = "Description B", id = "2", image = "imageB", model = "Model B", name = "Product 2", price = "20.0"), piece = "1")
        )

        viewModel.calculateTotalValue(cartModelList)

        assertEquals(40.0, viewModel.totalValue.value, 0.0)
    }
}
