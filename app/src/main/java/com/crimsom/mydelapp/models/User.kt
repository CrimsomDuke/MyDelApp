package com.crimsom.mydelapp.models

import com.crimsom.mydelapp.models.aux_models.Profile
import com.google.gson.annotations.SerializedName

data class User(
    var id : Int,
    @SerializedName("name") var username : String,
    @SerializedName("email") var email : String,
    @SerializedName("password") var password : String = "",
    @SerializedName("role") var role : Int = 1
) {
    @SerializedName("profile") var profile : Profile = Profile(0, 1);
}