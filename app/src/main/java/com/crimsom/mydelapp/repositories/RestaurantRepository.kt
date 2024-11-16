package com.crimsom.mydelapp.repositories

import com.crimsom.mydelapp.api.APIDeliveryService
import com.crimsom.mydelapp.models.Restaurant
import com.crimsom.mydelapp.utilities.HttpLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestaurantRepository {

    fun getRestaurants(
        token : String,
        onSuccess : (List<Restaurant>) -> Unit,
        onError : (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(APIDeliveryService::class.java)

        service.getRestaurants("Bearer $token").enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                if (response.isSuccessful) {
                    val restaurants = response.body()!!
                    onSuccess(restaurants)
                } else {
                    onError(Throwable("Error in the request: ${response.code()}"))
                }

                HttpLogger.logResponse(response)
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                onError(t)
            }
        })

    }

    fun getRestaurantById(
        token : String,
        id : Int,
        onSuccess : (Restaurant) -> Unit,
        onError : (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(APIDeliveryService::class.java)

        service.getRestaurantById(token = "Bearer $token", id = id).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    val restaurant = response.body()!!
                    onSuccess(restaurant)
                } else {
                    onError(Throwable("Error in the request: ${response.code()}"))
                }

                HttpLogger.logResponse(response)
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                onError(t)
            }
        })

    }

}