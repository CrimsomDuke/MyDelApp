package com.crimsom.mydelapp.models

import com.google.gson.annotations.SerializedName

data class OrderDetail(
    @SerializedName("id") var id : Int,
    @SerializedName("quantity", alternate = arrayOf("qty")) var quantity : Int,
    @SerializedName("price") var price : Double,
    @SerializedName("product") var product : Product
) {
    @SerializedName("product_id") var productId : Int = product.id;
}