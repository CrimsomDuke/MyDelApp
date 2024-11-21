package com.crimsom.mydelapp.ui.driver_mode.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.Restaurant
import com.crimsom.mydelapp.repositories.OrderRepository
import com.crimsom.mydelapp.repositories.RestaurantRepository
import com.crimsom.mydelapp.utilities.Auth

class DriverFullOrderViewModel : ViewModel() {

    private val _order = MutableLiveData<Order>();
    public val order : MutableLiveData<Order> = _order;

    private val _restaurant = MutableLiveData<Restaurant>();
    public val restaurant : MutableLiveData<Restaurant> = _restaurant;

    public fun getOrderData(token : String, orderId : Int){
        OrderRepository.getOrderById(token, orderId, {
            _order.postValue(it)
        }, {
            Log.e("DriverFullOrderViewModel", "Error getting order: ${it.message}")
        })
    }

    public fun getRestaurantData(token : String, orderId : Int){
        RestaurantRepository.getRestaurantById(token, orderId, {
            _restaurant.postValue(it)
        }, {
            Log.e("DriverFullOrderViewModel", "Error getting restaurant data: ${it.message}")
        })
    }

}