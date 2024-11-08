package com.crimsom.mydelapp.models

data class Order(
    var id : Int,
    var userId : Int,
    var restauranteId : Int,
    var total : Double,
    var choferId : Int,
    var latitude : Double,
    var longitud : Double,
    var estado : Int
) {
}