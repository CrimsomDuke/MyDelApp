package com.crimsom.mydelapp.models

data class Product(val id : Int, var nombre : String, var restaurantId : Int) {

    var descripcion : String = "";
    var imageUrl : String = "";
}