package com.napa.foodstore.data.network.api.model.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderItemRequest(
    @SerializedName("catatan")
    val notes: String?,
    @SerializedName("nama")
    val name: String?,
    @SerializedName("harga")
    val price: Int?,
    @SerializedName("qty")
    val qty: Int?
)
