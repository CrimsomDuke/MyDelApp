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
import com.crimsom.mydelapp.MainActivity
import com.crimsom.mydelapp.R
import com.crimsom.mydelapp.aux_interfaces.MapUpdateListener
import com.crimsom.mydelapp.databinding.FragmentDriverMapBinding
import com.crimsom.mydelapp.utilities.Auth
import com.crimsom.mydelapp.utilities.PermissionsUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DriverMapFragment : Fragment(), OnMapReadyCallback, MapUpdateListener {

    private var mMap: GoogleMap? = null
    private lateinit var binding : FragmentDriverMapBinding;
    private lateinit var fusedLocationClient: FusedLocationProviderClient;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDriverMapBinding.inflate(inflater, container, false)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        val mapFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)
        PermissionsUtil.requestMapPermissions(this, MainActivity.PERMISSION_REQUEST_CODE);

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

        fusedLocationClient.lastLocation.addOnSuccessListener {
                location ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)

                //asignar las coordenadas del usuario
                Auth.currentUserLatitude = location.latitude.toString();
                Auth.currentUserLongitude = location.longitude.toString();

                println("Las coordenas son: " + Auth.currentUserLatitude + " - " + Auth.currentUserLongitude);

                mMap?.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        currentLatLng,
                        15f
                    )
                )
            }
        }
    }

    override fun addMarker(location: LatLng, title: String) {
        mMap?.addMarker(
            MarkerOptions().position(
                location
            ).title(title)
        );

        mMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                location,
                15f
            )
        )
    }

    override fun setupOriginAndDestinyMarkers(
        origin: LatLng,
        originTitle: String,
        destiny: LatLng,
        destinyTitle: String
    ) {
        mMap?.clear();
        mMap?.addMarker(
            MarkerOptions().position(
                origin
            ).title(originTitle)
        );

        mMap?.addMarker(
            MarkerOptions().position(
                destiny
            ).title(destinyTitle)
        );
    }

    override fun cleanMarkers() {
        mMap?.clear();
    }

}