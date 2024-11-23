package com.crimsom.mydelapp.models.aux_models

import com.google.gson.annotations.SerializedName

data class DriverLocation(
    @SerializedName("latitude") val latitude : String,
    @SerializedName("longitude") val longitude : String
)
