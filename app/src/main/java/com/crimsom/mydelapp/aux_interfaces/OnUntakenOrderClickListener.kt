package com.crimsom.mydelapp.aux_interfaces

import com.crimsom.mydelapp.models.Order

interface OnUntakenOrderClickListener {
    fun onUntakenOrderClick(order : Order)
}