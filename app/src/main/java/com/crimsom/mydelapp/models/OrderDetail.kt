package com.crimsom.mydelapp.models

data class OrderDetail(
    var id : Int,
    var ordenId : Int,
    var productoId : Int,
    var cantidad : Int,
    var precioProducto : Double
) {
}