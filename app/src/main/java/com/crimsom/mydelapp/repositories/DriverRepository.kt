package com.crimsom.mydelapp.repositories

import com.crimsom.mydelapp.api.APIDeliveryService
import com.crimsom.mydelapp.models.Driver
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.models.aux_models.DriverLocation
import com.crimsom.mydelapp.utilities.HttpLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DriverRepository {

    fun sendDriverLocation(
        accessToken : String,
        driverLocation : DriverLocation,
        onSuccess : () -> Unit,
        onError : (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(APIDeliveryService::class.java)

        service.sendDriverLocation(token = "Bearer $accessToken", location = driverLocation).enqueue(object : Callback<Driver> {
                override fun onResponse(call: Call<Driver>, response: Response<Driver>) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError(Throwable("Error in the request: ${response.code()}"))
                    }
                    HttpLogger.logResponse(response)
                }
                override fun onFailure(call: Call<Driver>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }

    fun getOrdersOfDriver(
        accessToken : String,
        onSuccess : (List<Order>) -> Unit,
        onError : (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(APIDeliveryService::class.java)

        service.getOrdersOfDriver(token = "Bearer $accessToken").enqueue(object : Callback<List<Order>> {
                override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                    if (response.isSuccessful) {
                        onSuccess(response.body()!!)
                    } else {
                        onError(Throwable("Error in the request: ${response.code()}"))
                    }
                    HttpLogger.logResponse(response)
                }
                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }

}