package com.crimsom.mydelapp.utilities

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment

object PermissionsUtil {

    public fun requestMapPermissions(fragment : Fragment){
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (
            checkSelfPermission(fragment.requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(fragment.requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            println("Permissions already granted")
            return;
        }
        fragment.requestPermissions(permissions, 0)

    }

}