package com.crimsom.mydelapp.models

import com.crimsom.mydelapp.models.aux_models.OrderDetailOnSending
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Order(
    var id : Int,
    @SerializedName("user_id") var userId : Int,
    @SerializedName("restaurant_id") var restaurantId : Int,
    @SerializedName("total") var total : Double,
    @SerializedName("driver_id") var driverId : Int? = null,
    @SerializedName("latitude") var latitude : String,
    @SerializedName("longitude") var longitude : String,
    @SerializedName("status") var status : Int
) {
    @SerializedName("created_at") public var createdAt : Date = Date()
    @SerializedName("address") public var address : String = "Vacio"
    @SerializedName("delivery_proof") public var deliveryProofImg : String = "";

    @SerializedName("order_details") public var orderDetails : List<OrderDetail> = listOf()
    @SerializedName("details") public var orderDetailsOnSending : List<OrderDetailOnSending> = listOf()
}
