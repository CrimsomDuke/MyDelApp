package com.crimsom.mydelapp.repositories

import com.crimsom.mydelapp.api.APIDeliveryService
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.aux_models.CompleteOrderData
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

    fun createOrder(
        accessToken: String,
        order: Order,
        onSuccess: (Order) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance();
        val service = retrofit.create(APIDeliveryService::class.java);

        service.createOrder(token = "Bearer $accessToken", order = order).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val order = response.body()!!
                    onSuccess(order)
                } else {
                    onError(Throwable("Error in the request: ${response.code()}"))
                }

                HttpLogger.logResponse(response)
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                onError(t) // Pass the error to the callback
            }
        })
    }

    fun getOrdersOfUser(
        accessToken: String,
        onSuccess: (List<Order>) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance();
        val service = retrofit.create(APIDeliveryService::class.java);

        service.getOrdersOfUser(token = "Bearer $accessToken").enqueue(object : Callback<List<Order>> {
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

    fun getOrderById(
        accessToken: String,
        orderId: Int,
        onSuccess: (Order) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance();
        val service = retrofit.create(APIDeliveryService::class.java);

        service.getOrderById(token = "Bearer $accessToken", id = orderId).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val order = response.body()!!
                    onSuccess(order)
                } else {
                    onError(Throwable("Error in the request: ${response.code()}"))
                }

                HttpLogger.logResponse(response)
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                onError(t) // Pass the error to the callback
            }
        })
    }

    /**
     * DRIVERS ENDPOINTS
     * */

    fun acceptOrder(
        accessToken: String,
        orderId: Int,
        onSuccess: (Order) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance();
        val service = retrofit.create(APIDeliveryService::class.java);

        service.acceptOrder(token = "Bearer $accessToken", id = orderId).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val order = response.body()!!
                    onSuccess(order)
                } else {
                    onError(Throwable("Error in the request: ${response.code()}"))
                }

                HttpLogger.logResponse(response)
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                onError(t) // Pass the error to the callback
            }
        })
    }

    fun onMyWayOrder(
        accessToken: String,
        orderId: Int,
        onSuccess: (Order) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance();
        val service = retrofit.create(APIDeliveryService::class.java);

        service.onMyWayOrder(token = "Bearer $accessToken", id = orderId).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val order = response.body()!!
                    onSuccess(order)
                } else {
                    onError(Throwable("Error in the request: ${response.code()}"))
                }

                HttpLogger.logResponse(response)
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                onError(t) // Pass the error to the callback
            }
        })
    }

    fun deliveredOrder(
        accessToken: String,
        orderId: Int,
        onSuccess: (Order) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance();
        val service = retrofit.create(APIDeliveryService::class.java);

        service.deliveredOrder(token = "Bearer $accessToken", id = orderId).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val order = response.body()!!
                    onSuccess(order)
                } else {
                    onError(Throwable("Error in the request: ${response.code()}"))
                }

                HttpLogger.logResponse(response)
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                onError(t) // Pass the error to the callback
            }
        })
    }

}