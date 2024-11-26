package com.crimsom.mydelapp.utilities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


object CameraUtil {

    var onPictureTakenCall : (Bitmap) -> Unit = {
        println("Picture taken" + it.rowBytes)
    }

    fun launchCamera(launcher: ActivityResultLauncher<Intent>) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        launcher.launch(takePictureIntent)
    }

    fun getBitmapFromActivityResult(result: ActivityResult?): Bitmap? {
        return if (result?.resultCode == Activity.RESULT_OK) {
            val extras = result.data?.extras
            extras?.get("data") as? Bitmap
        } else {
            null
        }
    }


    fun bitmapToMultipartBody(bitmap: Bitmap, name: String): MultipartBody.Part {
        // Save bitmap to a temporary file
        val file = File.createTempFile("image", ".jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        // Convert file to MultipartBody.Part
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        return MultipartBody.Part.createFormData(name, file.name, requestFile)
    }

    private fun toMediaTypeOrNull(type : String) : MediaType?{
        return MediaType.parse(type)
    }


}