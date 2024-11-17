package com.crimsom.mydelapp.models.aux_models

import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.Restaurant
import com.crimsom.mydelapp.models.User

data class CompleteOrderData(var order: Order? = null, var restaurant : Restaurant? = null, var user: User? = null) {

    fun isComplete(): Boolean {
        return order != null && restaurant != null
    }
}