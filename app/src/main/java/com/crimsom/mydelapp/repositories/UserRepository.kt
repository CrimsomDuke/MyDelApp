package com.crimsom.mydelapp.repositories

import com.crimsom.mydelapp.api.APIDeliveryService
import com.crimsom.mydelapp.models.User
import com.crimsom.mydelapp.models.aux_models.LoginRequest
import com.crimsom.mydelapp.models.aux_models.LoginResponse
import com.crimsom.mydelapp.utilities.HttpLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {

    fun login(
        loginRequest: LoginRequest,
        onSuccess: (LoginResponse) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(APIDeliveryService::class.java)

        service.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()!!
                    onSuccess(loginResponse)
                } else {
                    onError(Throwable("Error in the request: ${response.code()}"))
                }

                HttpLogger.logResponse(response)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onError(t) // Pass the error to the callback
            }
        })
    }

    fun getMe(
        authToken : String,
        onSuccess: (User) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(APIDeliveryService::class.java)

        service.getMe("Bearer $authToken").enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    println(response.body())
                    val user = response.body()!!
                    onSuccess(user)
                } else {
                    onError(Throwable("Error in the request: ${response.code()}"))
                }

                HttpLogger.logResponse(response)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                onError(t) // Pass the error to the callback
            }
        })
    }

}