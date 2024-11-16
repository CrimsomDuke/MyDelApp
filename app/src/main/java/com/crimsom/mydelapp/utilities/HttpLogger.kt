package com.crimsom.mydelapp.utilities

object HttpLogger {

    public fun logResponse(response : retrofit2.Response<*>){
        println("Response code: ${response.code()}")
        println("Response body: ${response.body()}")
        println("Response error body: ${response.errorBody().toString()}")
        println("Response message: ${response.message()}")
        println("Response raw: ${response.raw()}")
    }

}