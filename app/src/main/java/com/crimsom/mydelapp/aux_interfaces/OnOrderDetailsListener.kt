package com.crimsom.mydelapp.aux_interfaces

interface OnOrderDetailsListener {
    fun onGoToOrderDetailsByAction();
    fun onGoToOrderDetailsById(orderId : Int);
}