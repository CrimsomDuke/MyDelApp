package com.crimsom.mydelapp.ui.customer_mode.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crimsom.mydelapp.models.Restaurant
import com.crimsom.mydelapp.models.User
import com.crimsom.mydelapp.repositories.RestaurantRepository
import com.crimsom.mydelapp.repositories.UserRepository
import com.crimsom.mydelapp.utilities.Auth

class MainCustomerViewModel : ViewModel() {
    private val _restaurantsList = MutableLiveData<List<Restaurant>>().apply {
        value = mutableListOf();
    };
    val restaurantsList : LiveData<List<Restaurant>> = _restaurantsList;

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

}