package com.napa.foodstore.data.network.api.model.menu

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MenusResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<MenuItemResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)
