package com.crimsom.mydelapp.utilities

import com.crimsom.mydelapp.models.User
import com.crimsom.mydelapp.models.aux_models.CompleteOrderData

object Auth {
    var IS_CURRENT_USER_DRIVER = false;

    var currentUser : User = User(0, "", "", "", 1)
    var currentUserId : Int = 0
    var cust_selectedRestaurantId : Int = 0;

    var driver_selectedCompleteOrderData : CompleteOrderData = CompleteOrderData()

    var access_token : String = "";

    var currentUserLatitude : String = "";
    var currentUserLongitude : String = "";

    public fun clearUserSession(){
        currentUser = User(0, "", "", "", 1)
        currentUserId = 0;
        IS_CURRENT_USER_DRIVER = false;
        cust_selectedRestaurantId = 0;
        access_token = "";

        driver_selectedCompleteOrderData = CompleteOrderData()

        currentUserLatitude = "";
        currentUserLongitude = "";
    }

    fun isDriver(user : User) : Boolean{
        return user.profile.role == 2
    }

    public fun clearCompleteOrderData(){
        driver_selectedCompleteOrderData = CompleteOrderData()
    }

    public fun getOrderStatusDescription(orderId : Int) : String{
        if(orderId == Constants.ORDER_STATUS_REQUESTED || orderId == null) {
            return "Solicitado"
        }else if(orderId == Constants.ORDER_STATUS_ACCEPTED){
            return "Aceptado"
        }else if(orderId == Constants.ORDER_STATUS_ON_WAY) {
            return "En camino"
        }else if(orderId == Constants.ORDER_STATUS_DELIVERED){
            return "Entregado"
        }

        return "Desconocido"
    }

}