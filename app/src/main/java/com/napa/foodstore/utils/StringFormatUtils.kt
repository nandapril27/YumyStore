package com.napa.foodstore.utils

import android.icu.text.NumberFormat
import android.icu.util.Currency

fun Int.toCurrencyFormat(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("IDR")
    return format.format(this)
}
