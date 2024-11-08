package com.crimsom.mydelapp

import com.crimsom.mydelapp.models.User

object FakeDB {

    var users = mutableListOf<User>(
        User(1, "user", "password", tipoUsuario = 1),
        User(2, "chofer", "password", tipoUsuario = 2)
    )

}