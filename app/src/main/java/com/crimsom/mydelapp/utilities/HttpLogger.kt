package com.crimsom.mydelapp.utilities

import android.util.Log

object HttpLogger {

    public fun logResponse(response : retrofit2.Response<*>){
        Log.i("HTTP_LOGGER UTIL" , "Response code: ${response.code()}")
        Log.i("HTTP_LOGGER UTIL" , "Response body: ${response.body()}")
        Log.i("HTTP_LOGGER UTIL" , "Response error body: ${response.errorBody().toString()}")
        Log.i("HTTP_LOGGER UTIL" , "Response message: ${response.message()}")
        Log.i("HTTP_LOGGER UTIL" , "Response raw: ${response.raw()}")
    }

}