package com.crimsom.mydelapp

import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.Product
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
        Order(3, 1, 3, 60.0, 2, 100.0, 100.0, 3),
        Order(4, 1, 1, 40.0, 2, 100.0, 100.0, 3),
        Order(5, 1, 2, 50.0, 2, 100.0, 100.0, 3),
    )

    var product = mutableListOf<Product>(
        Product(1, "Hamburguesa", 1),
        Product(2, "Papas", 1),
        Product(3, "Refresco", 1),
        Product(4, "Hamburguesa", 2),
        Product(5, "Papas", 2),
        Product(6, "Refresco", 2),
    )

    fun getProductsByRestaurantId(restaurantId: Int): List<Product> {
        return product.filter { it.restaurantId == restaurantId }
    }

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

    fun getUserById(userId: Int): User? {
        return users.find { it.id == userId }
    }

}