package com.crimsom.mydelapp.tasks

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.crimsom.mydelapp.aux_interfaces.OnDriverLocationUpdateListener
import com.crimsom.mydelapp.utilities.PermissionsUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class SendDriverLocationTask {

    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient;

    private lateinit var onDriverLocationUpdateListener : OnDriverLocationUpdateListener;

    private var UPDATE_INTERVAL : Long = 30000L;

    private lateinit var ACTIVITY : AppCompatActivity;

    private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, UPDATE_INTERVAL).apply {
        setWaitForAccurateLocation(false);
        setMinUpdateIntervalMillis(UPDATE_INTERVAL - 5000)
        setMaxUpdateDelayMillis(100)
        build()
    }

    private constructor(activity: AppCompatActivity){
        this.ACTIVITY = activity;

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                if(locationResult.locations.isEmpty()){
                    Log.e("SendDriverLocationTask", "No se encontraron ubicaciones");
                    return;
                }

                for (location in locationResult.locations) {
                    Log.i("SendDriverLocationTask", "Ubicación: ${location.latitude}, ${location.longitude}")
                    this@SendDriverLocationTask.onDriverLocationUpdateListener.onDriverLocationUpdate(location);
                }
            }
        }
    }

    companion object{
        private var _instance : SendDriverLocationTask? = null;
        public fun getInstance(activity: AppCompatActivity) : SendDriverLocationTask{
            if(_instance == null){
                _instance = SendDriverLocationTask(activity);
            }
            return _instance!!;
        }
    }


    public fun startTask(){

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(ACTIVITY)

        if(ActivityCompat.checkSelfPermission(ACTIVITY, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(ACTIVITY, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            PermissionsUtil.requestMapPermissions(ACTIVITY, 100);
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest.build(), locationCallback, null)

    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    public fun stopTask(){
        this.stopLocationUpdates();
    }

    public fun setOnDriverLocationUpdateListener(listener : OnDriverLocationUpdateListener){
        this.onDriverLocationUpdateListener = listener;
    }

    public fun setLocationUpdateInterval(interval : Long){
        this.UPDATE_INTERVAL = interval;
    }

}