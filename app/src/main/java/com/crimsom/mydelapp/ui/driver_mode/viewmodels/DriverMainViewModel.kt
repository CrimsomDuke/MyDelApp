package com.crimsom.mydelapp.ui.driver_mode.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.repositories.OrderRepository

class DriverMainViewModel : ViewModel() {

    private val _freeOrders = MutableLiveData<List<Order>>().apply {
        value = mutableListOf()
    }

    val freeOrders: MutableLiveData<List<Order>> = _freeOrders

    fun getFreeOrders(token : String) {
        OrderRepository.getFreeOrders(
            accessToken = token,
            onSuccess = { orders ->
                _freeOrders.value = orders
            },
            onError = { error ->
                Log.e("DriverMainViewModel", error.message!!)
            }
        )
    }

}