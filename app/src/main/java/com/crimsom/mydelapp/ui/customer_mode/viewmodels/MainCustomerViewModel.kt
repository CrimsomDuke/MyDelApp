package com.crimsom.mydelapp.ui.customer_mode.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.Restaurant
import com.crimsom.mydelapp.models.User
import com.crimsom.mydelapp.repositories.OrderRepository
import com.crimsom.mydelapp.repositories.RestaurantRepository
import com.crimsom.mydelapp.repositories.UserRepository
import com.crimsom.mydelapp.utilities.Auth
import com.crimsom.mydelapp.utilities.Constants

class MainCustomerViewModel : ViewModel() {
    private val _restaurantsList = MutableLiveData<List<Restaurant>>().apply {
        value = mutableListOf();
    };
    val restaurantsList : LiveData<List<Restaurant>> = _restaurantsList;

    private val _ordersList = MutableLiveData<List<Order>>().apply {
        value = mutableListOf();
    };
    val ordersList : LiveData<List<Order>> = _ordersList;

    private val _currentUser = MutableLiveData<User>();
    val currentUser : LiveData<User> = _currentUser;

    fun getCurrentUser(token : String){
        UserRepository.getMe(token, {
            _currentUser.value = it;
        }, {
            println(it.message)
        })
    }

    fun setCurrentUserFromAuth(){
        _currentUser.value = Auth.currentUser;
    }

    fun getRestaurants(token : String){
        RestaurantRepository.getRestaurants(token, {
            _restaurantsList.value = it;
        }, {
            println(it.message)
        })
    }

    fun getOrderOfUser(token: String){
        //Here we only want the orders that are not delivered
        OrderRepository.getOrdersOfUser(token, {
            _ordersList.value = it.filter { it.status != Constants.ORDER_STATUS_DELIVERED };
        }, {
            println(it.message)
        })
    }

}