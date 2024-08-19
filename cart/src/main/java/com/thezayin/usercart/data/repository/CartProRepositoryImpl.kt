package com.thezayin.usercart.data.repository

import com.thezayin.databases.databasae.CartDatabase
import com.thezayin.entities.CartModel
import com.thezayin.entities.HomeProductsModel
import com.thezayin.framework.utils.Response
import com.thezayin.usercart.domain.repository.CartProRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date

class CartProRepositoryImpl(
    private val cartDatabase: CartDatabase
) : CartProRepository {

    val sdf = SimpleDateFormat("dd/M/yyyy")
    private val currentDate: String = sdf.format(Date())

    override fun getAllProduct(): Flow<Response<List<CartModel>>> = flow {
        try {
            emit(Response.Loading)
            val cartList = cartDatabase.cartDao().getAllProduct()
            emit(Response.Success(cartList))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun addToCart(product: HomeProductsModel): Flow<Response<Boolean>> =
        flow {
            try {
                emit(Response.Loading)
                val cartModel = CartModel(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    totalPrice = product.price,
                    description = product.description,
                    date = currentDate,
                    imageUri = product.imageUri.toString(),
                    quantity = 1
                )
                cartDatabase.cartDao().updateCart(cartModel)
                emit(Response.Success(true))
            } catch (e: Exception) {
                emit(Response.Error(e.localizedMessage ?: "An error occurred"))
            }
        }

    override fun updateQuantity(
        id: String,
        quantity: Int,
        totalPrice: Int
    ): Flow<Response<Boolean>> =
        flow {
            try {
                emit(Response.Loading)
                val cartModel = cartDatabase.cartDao().getAllProduct().find { it.id == id }
                cartModel?.let {
                    cartModel.quantity = quantity
                    cartModel.totalPrice = totalPrice.toString()
                    cartDatabase.cartDao().updateCart(cartModel)
                    emit(Response.Success(true))
                } ?: emit(Response.Error("Product not found"))
            } catch (e: Exception) {
                emit(Response.Error(e.localizedMessage ?: "An error occurred"))
            }
        }

    override fun deleteFromCart(id: String): Flow<Response<Boolean>> {
        return flow {
            try {
                emit(Response.Loading)
                cartDatabase.cartDao().deleteProduct(id)
                emit(Response.Success(true))
            } catch (e: Exception) {
                emit(Response.Error(e.localizedMessage ?: "An error occurred"))
            }
        }
    }

    override fun deleteAllFromCart(): Flow<Response<Boolean>> {
        return flow {
            try {
                emit(Response.Loading)
                cartDatabase.cartDao().deleteAllProduct()
                emit(Response.Success(true))
            } catch (e: Exception) {
                emit(Response.Error(e.localizedMessage ?: "An error occurred"))
            }
        }
    }
}