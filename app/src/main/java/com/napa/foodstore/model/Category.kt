package com.napa.foodstore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Category(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val imgUrl: String
) : Parcelable
