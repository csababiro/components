package com.mobiversal.userlocation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*

/**
 * Created by Biro Csaba on 20/11/2020.
 */

private const val REQ_CODE_LOCATION_PERMISSION = 101

abstract class UserLocationFragment : Fragment() {

    private val TAG = UserLocationFragment::class.java.simpleName

    protected var rationaleTitle: String = ""
    protected var rationaleDescription: String = ""
    protected var rationaleOk: String = ""

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocation: Location? = null

    override fun onDestroy() {
        removeLocationUpdates()
        super.onDestroy()
    }

    protected fun removeLocationUpdates() {
        if (locationUpdateListener != null) {
            mFusedLocationClient?.removeLocationUpdates(locationUpdateListener)
            locationUpdateListener = null
            mFusedLocationClient = null
        }
    }

    protected fun removeLocationUpdatesListener() {
        if (locationUpdateListener != null) {
            mFusedLocationClient?.removeLocationUpdates(locationUpdateListener)
        }
    }

    protected fun prepareRetrieveLocation() {
        activity?.let { activity ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when {
                    hasLocationPermission(activity) -> getLocation(activity)
                    shouldShowRationale(activity) -> showRationaleDialog(activity)
                    else -> requestLocationPermission(activity)
                }
            } else
                getLocation(activity)
        }
    }

    private fun getLocation(activity: Activity) {
        if (mFusedLocationClient == null)
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        //getLastKnownLocation(mFusedLocationClient!!) not needed now
        initLocationUpdateListener(mFusedLocationClient)
    }

    private fun showRationaleDialog(activity: Activity) {
        showOkDialog(
            rationaleTitle,
            rationaleDescription,
            rationaleOk,
            positiveListener = { requestLocationPermission(activity) })
    }

    protected fun hasLocationPermission(context: Context) =
        hasFineLocationPermission(context) && hasCoarseLocationPermission(context)

    private fun hasFineLocationPermission(context: Context) = ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasCoarseLocationPermission(context: Context) = ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRationale(activity: Activity) =
        ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(mFusedLocationClient: FusedLocationProviderClient) {
        mFusedLocationClient.lastLocation
            .addOnSuccessListener { location -> newLocationDetected(location) }
            .addOnFailureListener { e ->
                Log.d(TAG,"Error trying to get last GPS location")
                e.printStackTrace()
            }
    }

    private var locationUpdateListener: LocationCallback? = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if (locationResult != null) {
                val location = locationResult.lastLocation
                if (location != null) {
                    Log.d(TAG,"Location update: $location")
                    newLocationDetected(location)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLocationUpdateListener(mFusedLocationClient: FusedLocationProviderClient?) {
        Log.d(TAG,"initLocationUpdateListener")
        val UPDATE_INTERVAL_MILLIS = 0L
        val FASTEST_UPDATE_INTERVAL_MILLIS = 0L
        val SMALLEST_DISPLACEMENT_METERS = 0f
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = UPDATE_INTERVAL_MILLIS
        locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_MILLIS
        locationRequest.smallestDisplacement = SMALLEST_DISPLACEMENT_METERS
        mFusedLocationClient?.requestLocationUpdates(locationRequest, locationUpdateListener, null)
    }

    private fun newLocationDetected(location: Location) {
        mLocation = location
        locationDetected(location)
    }

    protected open fun locationDetected(location: Location) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQ_CODE_LOCATION_PERMISSION -> locationPermissionResult()
        }
    }

    protected open fun requestLocationPermission(activity: Activity) = requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), REQ_CODE_LOCATION_PERMISSION)

    protected open fun locationPermissionResult() {
        activity?.let { activity ->
            when {
                hasLocationPermission(activity) -> getLocation(activity)
                shouldShowRationale(activity) -> showRationaleDialog(activity)
            }
        }
    }
}