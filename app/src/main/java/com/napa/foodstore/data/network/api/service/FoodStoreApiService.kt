package com.napa.foodstore.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.napa.foodstore.BuildConfig.BASE_URL
import com.napa.foodstore.data.network.api.model.category.CategoriesResponse
import com.napa.foodstore.data.network.api.model.menu.MenusResponse
import com.napa.foodstore.data.network.api.model.order.OrderRequest
import com.napa.foodstore.data.network.api.model.order.OrderResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface FoodStoreApiService {

    @GET("listmenu")
    suspend fun getMenus(@Query("c") category: String? = null): MenusResponse

    @GET("category")
    suspend fun getCategories(): CategoriesResponse

    @POST("order")
    suspend fun createOrder(@Body orderRequest: OrderRequest): OrderResponse

    companion object {
        @JvmStatic
        operator fun invoke(chucker: ChuckerInterceptor): FoodStoreApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(FoodStoreApiService::class.java)
        }
    }
}
