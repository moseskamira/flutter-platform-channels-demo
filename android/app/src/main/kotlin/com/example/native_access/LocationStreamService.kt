package com.example.native_access

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.*
import io.flutter.plugin.common.EventChannel

class LocationStreamService(private val activity: MainActivity) : EventChannel.StreamHandler {
    private var events: EventChannel.EventSink? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val location: Location = result.lastLocation ?: return

            val data = mapOf(
                "lat" to location.latitude,
                "lng" to location.longitude
            )
            events?.success(data)
        }
    }

    override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
        events = eventSink
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            3000L
        ).build()
        fusedLocationClient.requestLocationUpdates(
            request,
            locationCallback,
            activity.mainLooper
        )
    }

    override fun onCancel(arguments: Any?) {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        events = null
    }
}