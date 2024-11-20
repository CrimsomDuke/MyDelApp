package com.crimsom.mydelapp.ui.driver_mode.fragments.sub_fragments

import androidx.fragment.app.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.databinding.FragmentDriverMapBinding
import com.crimsom.mydelapp.utilities.Auth
import com.crimsom.mydelapp.utilities.PermissionsUtil
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DriverMapFragment : Fragment(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private lateinit var binding : FragmentDriverMapBinding;

    private lateinit var originLocation : LatLng;
    private lateinit var destinationLocation : LatLng;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDriverMapBinding.inflate(inflater, container, false)

        if(Auth.driver_selectedCompleteOrderData.isComplete()){
            originLocation = LatLng(
                Auth.driver_selectedCompleteOrderData.restaurant!!.latitude.toDouble(),
                Auth.driver_selectedCompleteOrderData.restaurant!!.longitude.toDouble()
            )

            destinationLocation = LatLng(
                Auth.driver_selectedCompleteOrderData.order!!.latitude.toDouble(),
                Auth.driver_selectedCompleteOrderData.order!!.longitude.toDouble()
            )
        }

        println("Origin location: $originLocation")

        val mapFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)
        PermissionsUtil.requestMapPermissions(this);

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap?.uiSettings?.isZoomControlsEnabled = true
        mMap?.uiSettings?.isCompassEnabled = true

        mMap?.addMarker(
            MarkerOptions().position(
                originLocation
            ).title(Auth.driver_selectedCompleteOrderData.restaurant!!.name)
        )

        mMap?.addMarker(
            MarkerOptions().position(
                destinationLocation
            ).title("Destino")
        )

        mMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                originLocation,
                15f
            )
        )
        enableLocationOnMap()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableLocationOnMap()
        } else {
            Toast.makeText(
                requireContext(),
                "Necesita habilitar la ubicación en la configuración de la app",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", requireActivity().packageName, null)
            })
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableLocationOnMap() {
        mMap?.isMyLocationEnabled = true
    }

}