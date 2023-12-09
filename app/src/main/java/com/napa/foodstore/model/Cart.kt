package com.napa.foodstore.model

import com.napa.foodstore.data.network.api.model.order.OrderItemRequest

data class Cart(
    var id: Int? = null,
    var menuId: String,
    var menuName: String,
    var menuPrice: Int,
    var itemQuantity: Int = 0,
    var menuImgUrl: String,
    var itemNotes: String? = null
)

fun Cart.toOrderItemRequest() = OrderItemRequest(
    notes = this.itemNotes.orEmpty(),
    price = this.menuPrice,
    name = this.menuName,
    qty = this.itemQuantity
)

fun Collection<Cart>.toOrderItemRequestList() = this.map { it.toOrderItemRequest() }
