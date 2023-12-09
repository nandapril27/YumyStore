package com.napa.foodstore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Int,
    val priceFormat: String,
    val menuImgUrl: String,
    val desc: String,
    val location: String
) : Parcelable
