package com.crimsom.mydelapp.utilities

import java.text.SimpleDateFormat
import java.util.Date

object Constants {

    const val  ORDER_STATUS_REQUESTED = 1;
    const val  ORDER_STATUS_ACCEPTED = 2;
    const val  ORDER_STATUS_ON_WAY = 3;
    const val  ORDER_STATUS_DELIVERED = 4;

    private var formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

    public fun getFullDateInFormat(date : Date) : String{
        return formatter.format(date);
    }
}