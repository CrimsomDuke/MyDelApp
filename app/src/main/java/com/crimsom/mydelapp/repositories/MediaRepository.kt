package com.crimsom.mydelapp.repositories

import android.graphics.Bitmap
import com.crimsom.mydelapp.api.APIDeliveryService
import com.crimsom.mydelapp.models.Order
import com.crimsom.mydelapp.utilities.CameraUtil
import com.crimsom.mydelapp.utilities.HttpLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MediaRepository {

    fun sendDeliveryProof(
        acccesToken : String,
        orderId : Int,
        fileBitmap : Bitmap,
        onSuccess : () -> Unit,
        onError : (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(APIDeliveryService::class.java)

        val filePart = CameraUtil.bitmapToMultipartBody(fileBitmap, "image")

        service.sendDeliveryProof(token = "Bearer $acccesToken", id = orderId, file =  filePart).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(Throwable("Error in the request: ${response.code()}"))
                }
                HttpLogger.logResponse(response)
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                onError(t)
            }
        })
    }

}