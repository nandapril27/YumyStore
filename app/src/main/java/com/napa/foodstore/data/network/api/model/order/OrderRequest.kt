package com.napa.foodstore.data.network.api.model.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderRequest(
    @SerializedName("orders")
    val orders: List<OrderItemRequest>?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("username")
    val username: String?
)
