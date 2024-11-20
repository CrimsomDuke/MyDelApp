package com.crimsom.mydelapp.ui.customer_mode.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.Restaurant
import com.crimsom.mydelapp.models.aux_models.CompleteOrderData
import com.crimsom.mydelapp.repositories.OrderRepository
import com.crimsom.mydelapp.repositories.RestaurantRepository

class FullOrderMapViewModel : ViewModel() {

    private val _orderData = MutableLiveData<Order>()
    val orderData : MutableLiveData<Order> = _orderData;

    private val _restaurantData = MutableLiveData<Restaurant>()
    val restaurantData : MutableLiveData<Restaurant> = _restaurantData;


    fun getOrderData(token : String, orderId: Int){
        OrderRepository.getOrderById(token, orderId, {
            _orderData.postValue(it)
        }, {
            Log.e("FullOrderMapViewModel", "Error getting order: ${it.message}")
        })
    }

    fun getRestaurantData(token : String, orderId: Int){
        RestaurantRepository.getRestaurantById(token, orderId, {
            _restaurantData.value = it
        }, {
            Log.e("FullOrderMapViewModel", "Error getting restaurant data: ${it.message}")
        })
    }

    fun setOrderData(order: Order){
        _orderData.value = order;
    }

}