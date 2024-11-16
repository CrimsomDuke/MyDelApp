package com.crimsom.mydelapp.models

import com.google.gson.annotations.SerializedName

data class Product(
    val id : Int,
    var name : String,
    @SerializedName("restaurant_id") var restaurantId : Int,
    var price : Int
) {

    var description : String = "";
    var image : String = "";
}