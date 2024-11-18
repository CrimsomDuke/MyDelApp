package com.crimsom.mydelapp.models.aux_models

import com.crimsom.mydelapp.models.Product
import com.google.gson.annotations.SerializedName


/**
 *  AUX MODEL FOR SENDING DATA IN CREATION OF ORDERS, THE FIELD IN THE API IS CALLED qty instead of quantity
 * */

data class OrderDetailOnSending(
    @SerializedName("id") var id : Int,
    @SerializedName("qty") var qty : Int,
    @SerializedName("price") var price : Double,
    @SerializedName("product") var product: Product
) {
    @SerializedName("product_id")
    var productId: Int = product.id;
}