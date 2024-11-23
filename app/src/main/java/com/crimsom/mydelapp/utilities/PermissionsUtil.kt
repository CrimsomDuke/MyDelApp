package com.crimsom.mydelapp.utilities

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionsUtil {

    private val LOCATION_PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun requestMapPermissions(activity: AppCompatActivity, requestCode: Int) {
        if (!hasLocationPermissions(activity)) {
            ActivityCompat.requestPermissions(activity, LOCATION_PERMISSIONS, requestCode)
        } else {
            println("Permissions already granted")
        }
    }

    fun requestMapPermissions(fragment: Fragment, requestCode: Int) {
        if (!hasLocationPermissions(fragment.requireContext())) {
            fragment.requestPermissions(LOCATION_PERMISSIONS, requestCode)
        } else {
            println("Permissions already granted")
        }
    }

    fun hasLocationPermissions(context: android.content.Context): Boolean {
        return LOCATION_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

}