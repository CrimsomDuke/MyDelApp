package com.crimsom.mydelapp.utilities

import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.User
import com.crimsom.mydelapp.models.aux_models.CompleteOrderData

object Auth {
    var IS_CURRENT_USER_DRIVER = false;

    var currentUser : User = User(0, "", "", "", 1)
    var currentUserId : Int = 0
    var cust_selectedRestaurantId : Int = 0;


    var driver_selectedCompleteOrderData : CompleteOrderData = CompleteOrderData()

    var access_token : String = "";

    public fun clearUserSession(){
        currentUser = User(0, "", "", "", 1)
        currentUserId = 0;
        IS_CURRENT_USER_DRIVER = false;
        cust_selectedRestaurantId = 0;
        access_token = "";

        driver_selectedCompleteOrderData = CompleteOrderData()
    }

    fun isDriver(user : User) : Boolean{
        return user.profile.role == 2
    }

    public fun clearCompleteOrderData(){
        driver_selectedCompleteOrderData = CompleteOrderData()
    }

}