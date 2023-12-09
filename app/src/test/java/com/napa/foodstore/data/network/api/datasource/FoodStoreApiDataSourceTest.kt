package com.napa.foodstore.data.network.api.datasource

import com.napa.foodstore.data.network.api.model.category.CategoriesResponse
import com.napa.foodstore.data.network.api.model.menu.MenusResponse
import com.napa.foodstore.data.network.api.model.order.OrderRequest
import com.napa.foodstore.data.network.api.model.order.OrderResponse
import com.napa.foodstore.data.network.api.service.FoodStoreApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FoodStoreApiDataSourceTest {

    @MockK
    lateinit var service: FoodStoreApiService

    private lateinit var dataSource: FoodStoreDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FoodStoreApiDataSource(service)
    }

    @Test
    fun getMenus() {
        runTest {
            val mockResponse = mockk<MenusResponse>(relaxed = true)
            coEvery { service.getMenus(any()) } returns mockResponse
            val response = dataSource.getMenus("makanan")
            coVerify { service.getMenus(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCategories() {
        runTest {
            val mockResponse = mockk<CategoriesResponse>(relaxed = true)
            coEvery { service.getCategories() } returns mockResponse
            val response = dataSource.getCategories()
            coVerify { service.getCategories() }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockResponse = mockk<OrderResponse>(relaxed = true)
            val mockRequest = mockk<OrderRequest>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val response = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(response, mockResponse)
        }
    }
}
