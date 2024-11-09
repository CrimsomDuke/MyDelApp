package com.crimsom.mydelapp.models

import java.util.Date

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
    public var fechaHora : Date = Date()
    public var direccion : String = "Test temporal"
}
