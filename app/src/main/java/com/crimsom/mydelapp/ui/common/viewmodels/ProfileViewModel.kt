package com.crimsom.mydelapp.ui.common.viewmodels

import androidx.lifecycle.MutableLiveData
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.User
import com.crimsom.mydelapp.repositories.OrderRepository
import com.crimsom.mydelapp.repositories.UserRepository
import com.crimsom.mydelapp.utilities.Auth

class ProfileViewModel {

    private val _user = MutableLiveData<User>();
    val user : MutableLiveData<User> = _user;

    private val _orders = MutableLiveData<List<Order>>().apply {
        value = mutableListOf()
    };
    val orders : MutableLiveData<List<Order>> = _orders;

    fun getCurrentUser(token : String){
        UserRepository.getMe(token, {
            _user.value = it;
        }, {
            println(it.message)
        })
    }

    fun getCurrentUserFromAuth(){
        _user.value = Auth.currentUser;
    }

    fun getOrdersHistory(token : String){
        OrderRepository.getOrdersOfUser(token, {
            _orders.value = it;
        }, {
            println(it.message)
        })
    }

}