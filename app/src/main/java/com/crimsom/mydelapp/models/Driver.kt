package com.crimsom.mydelapp.models

import com.google.gson.annotations.SerializedName

data class Driver(var id : Int, var userId : Int) {
    @SerializedName("latitude") var latitude : String = "";
    @SerializedName("longitude") var longitude : String = "";
    @SerializedName("user") var user : User = User(0, "", "", "");
}