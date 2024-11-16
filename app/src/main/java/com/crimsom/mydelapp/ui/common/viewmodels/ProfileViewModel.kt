package com.crimsom.mydelapp.ui.common.viewmodels

import androidx.lifecycle.MutableLiveData
import com.crimsom.mydelapp.models.User
import com.crimsom.mydelapp.repositories.UserRepository
import com.crimsom.mydelapp.utilities.Auth

class ProfileViewModel {

    private val _user = MutableLiveData<User>();
    val user : MutableLiveData<User> = _user;

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

}