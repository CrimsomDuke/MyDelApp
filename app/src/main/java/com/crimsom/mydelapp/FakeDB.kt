package com.crimsom.mydelapp

import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.Restaurant
import com.crimsom.mydelapp.models.User
import retrofit2.http.Body

object FakeDB {

    var users = mutableListOf<User>(
        User(1, "user", "password", tipoUsuario = 1),
        User(2, "chofer", "password", tipoUsuario = 2)
    )

    var restaurants = mutableListOf<Restaurant>(
        Restaurant(1, "McDonalds", 2),
        Restaurant(2, "Burger King", 2),
        Restaurant(3, "KFC", 2),
    )

    var orders = mutableListOf<Order>(
        Order(1, 1, 1, 40.0, 2, 100.0, 100.0, 1),
        Order(2, 1, 2, 50.0, 2, 100.0, 100.0, 1),
    )

    fun login(email: String, password: String): User? {
        return users.find { it.email == email && it.password == password }
    }

    fun register(email: String, password: String): User {
        var newUser = User(users.size + 1, email, password)
        users.add(newUser)
        return newUser
    }

    fun getOrdersByUserId(userId: Int): List<Order> {
        return orders.filter { it.userId == userId }
    }

    fun isDriver(user : User) : Boolean{
        return user.tipoUsuario == 2
    }

}