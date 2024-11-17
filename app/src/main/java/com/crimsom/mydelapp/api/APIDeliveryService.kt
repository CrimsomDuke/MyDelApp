package com.crimsom.mydelapp.api

import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.Product
import com.crimsom.mydelapp.models.Restaurant
import com.crimsom.mydelapp.models.User
import com.crimsom.mydelapp.models.aux_models.LoginRequest
import com.crimsom.mydelapp.models.aux_models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface APIDeliveryService {


    /*
    *
    * auth module
    *
    * */

    @POST("users/login")
    fun login(
        @Body loginRequest : LoginRequest,
    ): Call<LoginResponse>

    @POST("users")
    fun register(@Body user : User): Call<User>

    @GET("me")
    fun getMe(
        @Header("Authorization") token : String,
        @Header("Accept") accept : String = "application/json"
    ): Call<User>

    /*
    * RESTAURANT MODULE
     */

    @GET("restaurants")
    fun getRestaurants(
        @Header("Authorization") token : String,
        @Header("Accept") accept : String = "application/json"
    ) : Call<List<Restaurant>>

    @GET("restaurants/{id}")
    fun getRestaurantById(
        @Header("Accept") accept : String = "application/json",
        @Header("Authorization") token : String,
        @Path("id") id : Int
    ) : Call<Restaurant>


    /*
    * PRODUCT MODULE
     */

    @GET("products")
    fun getProducts(
        @Header("Authorization") token : String,
        @Header("Accept") accept : String = "application/json"
    ) : Call<List<Product>>


    /*
    * ORDERS MODULE
     */

    @GET("orders/free")
    fun getFreeOrders(
        @Header("Accept") accept : String = "application/json",
        @Header("Authorization") token : String,
    ) : Call<List<Order>>

}