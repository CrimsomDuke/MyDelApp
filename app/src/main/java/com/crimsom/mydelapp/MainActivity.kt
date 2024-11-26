package com.crimsom.mydelapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.crimsom.mydelapp.tasks.SendDriverLocationTask
import com.crimsom.mydelapp.tasks.UpdateOrderStatusTask
import com.crimsom.mydelapp.utilities.PermissionsUtil

class MainActivity : AppCompatActivity() {

    companion object {
        const val PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onStart() {
        super.onStart()
        PermissionsUtil.requestMapPermissions(this, PERMISSION_REQUEST_CODE)
        PermissionsUtil.requestCameraPermissions(this, MainActivity.PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            val allGranted = grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (allGranted) {
                Log.i("MainActivity", "Permissions granted")
            } else {
                Toast.makeText(
                    this,
                    "Necesita habilitar la ubicación en la configuración de la app",
                    Toast.LENGTH_SHORT
                ).show()
                openAppSettings()
            }
        }
    }

    private fun openAppSettings() {
        startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        //stop all the tasks
        val sendDriverLocationTask = SendDriverLocationTask.getInstance(this);
        sendDriverLocationTask.stopTask();

        val updateOrderStatusTask = UpdateOrderStatusTask.getInstance(this);
        updateOrderStatusTask.stopTask();
    }


    public fun startSendDriverLocationTask(){
        val sendDriverLocationTask = SendDriverLocationTask.getInstance(this);
        sendDriverLocationTask.startTask();
    }
}