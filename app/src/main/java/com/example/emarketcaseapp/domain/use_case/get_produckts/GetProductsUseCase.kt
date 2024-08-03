package com.example.emarketcaseapp.domain.use_case.get_produckts

import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.data.remote.dto.toListProduct
import com.example.emarketcaseapp.domain.model.Product
import com.example.emarketcaseapp.domain.repository.MarketRepository
import com.example.emarketcaseapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOError
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val repository: MarketRepository) {
    fun executeGetProducts(): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val products = repository.getProducts()
            if (products.Products.isNotEmpty())
                emit(Resource.Success(products.toListProduct()))
            else {
                emit(Resource.Error("Product Not Found"))
            }
        } catch (_: IOError) {
            emit(Resource.Error("Internet Problem"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

}