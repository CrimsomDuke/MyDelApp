package com.crimsom.mydelapp

import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.Product
import com.crimsom.mydelapp.models.Restaurant
import com.crimsom.mydelapp.models.User

object FakeDB {

    var users = mutableListOf<User>(
        User(1, "User", "user", "password", role = 1),
        User(2, "Chofer" , "chofer", "password", role = 2)
    )

    var restaurants = mutableListOf<Restaurant>(
        Restaurant(1, "McDonalds"),
        Restaurant(2, "Burger King"),
        Restaurant(3, "KFC"),
    )

    var orders = mutableListOf<Order>(
        Order(1, 1, 1, 40.0, 2, "100.0", "100.0", 1),
        Order(2, 1, 2, 50.0, 2, "100.0", "100.0", 1),
        Order(3, 1, 3, 60.0, 2, "100.0", "100.0", 0),
        Order(4, 1, 1, 40.0, 2, "100.0", "100.0", 0),
        Order(5, 1, 2, 50.0, 2, "100.0", "100.0", 0),
        Order(1, 1, 1, 40.0, 2, "100.0", "100.0", 0),
        Order(4, 1, 1, 40.0, 2, "100.0", "100.0", 3),
        Order(5, 1, 2, 50.0, 2, "100.0", "100.0", 3),
        Order(1, 1, 1, 40.0, 2, "100.0", "100.0", 3),
        Order(4, 1, 1, 40.0, 2, "100.0", "100.0", 1),
        Order(5, 1, 2, 50.0, 2, "100.0", "100.0", 1),
        Order(1, 1, 1, 40.0, 2, "100.0", "100.0", 0),
        Order(4, 1, 1, 40.0, 2, "100.0", "100.0", 2),
    )

    var product = mutableListOf<Product>(
        Product(1, "Hamburguesa", 1, 32),
        Product(2, "Papas", 1, 20),
        Product(3, "Refresco", 1, 10),
        Product(4, "Hamburguesa", 2, 25),
        Product(5, "Papas", 2, 45),
        Product(6, "Refresco", 2, 46),
        Product(7, "Hamburguesa Loca", 1, 36),
        Product(8, "Papas Locas", 1, 22),
        Product(9, "Refresco Loca", 1, 12),
        Product(10, "Hamburguesa Missisipi", 2, 27),
        Product(11, "Papas Missisipi", 1, 47),
        Product(12, "Refresco Missisipi", 1, 48),

    )

    fun getProductsByRestaurantId(restaurantId: Int): List<Product> {
        return product.filter { it.restaurantId == restaurantId }
    }

    fun login(user : User): User? {
        return users.find { it.email == user.email && it.password == user.password }
    }

    fun register(user : User): User {
        var newUser = User(users.size + 1, user.username, user.email, user.password, user.role)
        users.add(newUser)
        return newUser
    }

    fun getOrdersByUserId(userId: Int): List<Order> {
        return orders.filter { it.userId == userId }
    }

    fun getUntakenOrders(): List<Order> {
        return orders.filter { it.status == 0 }
    }

    fun isDriver(user : User) : Boolean{
        return user.role == 2
    }

    fun getUserById(userId: Int): User? {
        return users.find { it.id == userId }
    }

}