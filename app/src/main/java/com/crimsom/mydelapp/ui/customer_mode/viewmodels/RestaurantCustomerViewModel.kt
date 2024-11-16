package com.crimsom.mydelapp.ui.customer_mode.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crimsom.mydelapp.models.Product
import com.crimsom.mydelapp.models.Restaurant
import com.crimsom.mydelapp.repositories.RestaurantRepository

class RestaurantCustomerViewModel : ViewModel() {

    private val _restaurant = MutableLiveData<Restaurant>().apply {
        value = Restaurant(0, "Default Restaurant")
    };
    val restaurant : LiveData<Restaurant> = _restaurant;


    fun getProductsByRestaurantId(token : String, restaurantId : Int){
        RestaurantRepository.getRestaurantById(token, restaurantId, {
            _restaurant.value = it;
        }, {
            println(it.message)
        })
    }

}