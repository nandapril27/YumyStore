package com.napa.foodstore.data.network.api.model.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.napa.foodstore.model.Category

@Keep
data class CategoryResponse(
    @SerializedName("image_url")
    val imgUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun CategoryResponse.toCategory() = Category(
    imgUrl = this.imgUrl.orEmpty(),
    name = this.name.orEmpty()
)

fun Collection<CategoryResponse>.toCategoryList() = this.map {
    it.toCategory()
}
