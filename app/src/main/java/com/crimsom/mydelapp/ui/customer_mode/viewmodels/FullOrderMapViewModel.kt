package com.crimsom.mydelapp.ui.customer_mode.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crimsom.mydelapp.models.Order

class FullOrderMapViewModel : ViewModel() {

    private val _order = MutableLiveData<Order>()
    val order = _order

    fun getOrder(orderId: Int){

    }

}