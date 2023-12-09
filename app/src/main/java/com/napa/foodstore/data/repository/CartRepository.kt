package com.napa.foodstore.data.repository

import com.napa.foodstore.data.local.database.datasource.CartDataSource
import com.napa.foodstore.data.local.database.entity.CartEntity
import com.napa.foodstore.data.local.database.mapper.toCartEntity
import com.napa.foodstore.data.local.database.mapper.toCartList
import com.napa.foodstore.data.network.api.datasource.FoodStoreDataSource
import com.napa.foodstore.data.network.api.model.order.OrderRequest
import com.napa.foodstore.model.Cart
import com.napa.foodstore.model.Menu
import com.napa.foodstore.model.toOrderItemRequestList
import com.napa.foodstore.utils.ResultWrapper
import com.napa.foodstore.utils.proceed
import com.napa.foodstore.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.lang.IllegalStateException

interface CartRepository {
    fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Int>>>
    suspend fun createCart(menu: Menu, totalQuantity: Int): Flow<ResultWrapper<Boolean>>
    suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun deleteAllCarts()
    suspend fun createOrder(items: List<Cart>, totalPrice: Int, username: String): Flow<ResultWrapper<Boolean>>
}
class CartRepositoryImpl(
    private val dataSource: CartDataSource,
    private val foodStoreDataSource: FoodStoreDataSource
) : CartRepository {

    override fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Int>>> {
        return dataSource.getAllCarts().map {
            proceed {
                val cartList = it.toCartList()
                val totalPrice = cartList.sumOf {
                    val quantity = it.itemQuantity
                    val pricePerITem = it.menuPrice
                    quantity * pricePerITem
                }
                Pair(cartList, totalPrice)
            }
        }.map {
            if (it.payload?.first?.isEmpty() == true) {
                ResultWrapper.Empty(it.payload)
            } else {
                it
            }
        }.catch {
            emit(ResultWrapper.Error(exception = Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

    override suspend fun createCart(
        menu: Menu,
        totalQuantity: Int
    ): Flow<ResultWrapper<Boolean>> {
        return menu.id?.let { menuId ->
            proceedFlow {
                val affectedRow = dataSource.insertCart(
                    CartEntity(
                        itemQuantity = totalQuantity,
                        menuImgUrl = menu.menuImgUrl,
                        menuName = menu.name,
                        menuPrice = menu.price,
                        menuId = menuId
                    )
                )
                affectedRow > 0
            }
        } ?: flow {
            emit(ResultWrapper.Error(IllegalStateException("Menu ID not found")))
        }
    }

    override suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity -= 1
        }
        return if (modifiedCart.itemQuantity <= 0) {
            proceedFlow { dataSource.deleteCart((modifiedCart.toCartEntity())) > 0 }
        } else {
            proceedFlow { dataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
        }
    }

    override suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity += 1
        }
        return proceedFlow { dataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
    }

    override suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateCart(item.toCartEntity()) > 0 }
    }

    override suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.deleteCart(item.toCartEntity()) > 0 }
    }

    override suspend fun deleteAllCarts() {
        dataSource.deleteAllCarts()
    }

    override suspend fun createOrder(items: List<Cart>, totalPrice: Int, username: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val orderItem = items.toOrderItemRequestList()
            val orderRequest = OrderRequest(orderItem, totalPrice, username)
            foodStoreDataSource.createOrder(orderRequest).status == true
        }
    }
}
