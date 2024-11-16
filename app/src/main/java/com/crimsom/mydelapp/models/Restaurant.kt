package com.crimsom.mydelapp.models

import com.google.gson.annotations.SerializedName

data class Restaurant(
    var id : Int,
    var name : String
) {

    var address : String = "";
    var latitude : String = "";
    var longitude : String = "";
    @SerializedName("logo") var logoUrl : String = "";

    @SerializedName("products") var productsList : List<Product> = mutableListOf();

}