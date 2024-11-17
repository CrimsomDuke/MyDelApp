package com.crimsom.mydelapp.repositories

import com.crimsom.mydelapp.api.APIDeliveryService
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.utilities.HttpLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object OrderRepository {

    fun getFreeOrders(
        accessToken : String,
        onSuccess: (List<Order>) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(APIDeliveryService::class.java)

        service.getFreeOrders(token = "Bearer $accessToken").enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body()!!
                    onSuccess(orders)
                } else {
                    onError(Throwable("Error in the request: ${response.code()}"))
                }

                HttpLogger.logResponse(response)
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                onError(t) // Pass the error to the callback
            }
        })
    }

}