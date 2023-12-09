package com.napa.foodstore.model

import com.napa.foodstore.data.network.api.model.order.OrderItemRequest

data class OrderItem(
    val notes: String,
    val name: String,
    val price: Int,
    val qty: Int
)
fun OrderItem.toOrderItemRequest() = OrderItemRequest(
    notes = this.notes,
    price = this.price,
    name = this.name,
    qty = this.qty
)

fun Collection<OrderItem>.toOrderItemRequestList() = this.map { it.toOrderItemRequest() }
