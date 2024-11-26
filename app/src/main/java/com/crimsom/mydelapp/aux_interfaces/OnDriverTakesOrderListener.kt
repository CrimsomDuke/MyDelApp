package com.crimsom.mydelapp.aux_interfaces

import android.graphics.Bitmap

interface OnDriverTakesOrderListener {
    fun onDriverTakesOrder()
    fun onDriverIsOnTheWay()
    fun onDriverArrived()
}