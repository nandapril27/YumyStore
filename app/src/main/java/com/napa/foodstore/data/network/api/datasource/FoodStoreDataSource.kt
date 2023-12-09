package com.napa.foodstore.data.network.api.datasource

import com.napa.foodstore.data.network.api.model.category.CategoriesResponse
import com.napa.foodstore.data.network.api.model.menu.MenusResponse
import com.napa.foodstore.data.network.api.model.order.OrderRequest
import com.napa.foodstore.data.network.api.model.order.OrderResponse
import com.napa.foodstore.data.network.api.service.FoodStoreApiService

interface FoodStoreDataSource {
    suspend fun getMenus(category: String? = null): MenusResponse
    suspend fun getCategories(): CategoriesResponse
    suspend fun createOrder(orderRequest: OrderRequest): OrderResponse
}

class FoodStoreApiDataSource(private val service: FoodStoreApiService) : FoodStoreDataSource {
    override suspend fun getMenus(category: String?): MenusResponse {
        return service.getMenus(category)
    }

    override suspend fun getCategories(): CategoriesResponse {
        return service.getCategories()
    }

    override suspend fun createOrder(orderRequest: OrderRequest): OrderResponse {
        return service.createOrder(orderRequest)
    }
}
