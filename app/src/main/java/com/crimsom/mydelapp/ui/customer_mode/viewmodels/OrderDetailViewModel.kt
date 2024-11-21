package com.crimsom.mydelapp.ui.customer_mode.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.Restaurant
import com.crimsom.mydelapp.repositories.OrderRepository
import com.crimsom.mydelapp.repositories.RestaurantRepository

class OrderDetailViewModel : ViewModel() {

    private var _order = MutableLiveData<Order>();
    val order : MutableLiveData<Order> = _order;

    private var _restaurant = MutableLiveData<Restaurant>();
    val restaurant : MutableLiveData<Restaurant> = _restaurant;

    public fun getOrderData(token : String, orderId : Int){
        OrderRepository.getOrderById(token, orderId, {
            _order.value = it;
        }, {
            println(it.message)
        })
    }

    public fun getRestaurantData(token : String, restaurantId : Int){
        RestaurantRepository.getRestaurantById(token, restaurantId, {
            _restaurant.value = it;
        }, {
            println(it.message)
        })
    }

}