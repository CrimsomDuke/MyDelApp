package com.crimsom.mydelapp.utilities

import com.crimsom.mydelapp.models.User

object Auth {
    var IS_CURRENT_USER_DRIVER = false;

    var currentUser : User = User(0, "", "", "", 1)
    var currentUserId : Int = 0
    var selectedRestaurantId : Int = 0;

    var access_token : String = "";

    public fun clearUserSession(){
        currentUser = User(0, "", "", "", 1)
        currentUserId = 0;
        IS_CURRENT_USER_DRIVER = false;
        selectedRestaurantId = 0;
        access_token = "";
    }

    fun isDriver(user : User) : Boolean{
        return user.profile.role == 2
    }

}