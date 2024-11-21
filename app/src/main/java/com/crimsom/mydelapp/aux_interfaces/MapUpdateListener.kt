package com.crimsom.mydelapp.aux_interfaces

import com.google.android.gms.maps.model.LatLng

interface MapUpdateListener {

    public fun addMarker(location : LatLng, title : String)
    public fun setupOriginAndDestinyMarkers(origin : LatLng, originTitle : String, destiny : LatLng, destinyTitle : String)
    public fun cleanMarkers();

}