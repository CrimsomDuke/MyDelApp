package com.crimsom.mydelapp.models

data class Restaurant(var id : Int, var nombre : String, var propietarioId : Int) {

    var direccion : String = "";
    var latitud : Double = 0.0;
    var longitud : Double = 0.0;
    var logoUrl : String = "";

}