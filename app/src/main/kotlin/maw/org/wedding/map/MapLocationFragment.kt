package maw.org.wedding.map

import android.Manifest
import android.app.Fragment
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v13.app.FragmentCompat
import android.support.v4.app.ActivityCompat

import com.google.android.gms.maps.MapFragment


class MapLocationFragment : MapFragment(), LocationRequester {

    lateinit var mMapController: MapController
    lateinit var mLocationPermissionListener: LocationPermissionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mMapController = MapController(context, this)
        getMapAsync(mMapController)
    }

    override fun enableCurrentLocation(locationPermissionListener: LocationPermissionListener) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionListener.locationAvailable()
        } else {
            val permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            mLocationPermissionListener = locationPermissionListener
            FragmentCompat.requestPermissions(this, permissionsArray, REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionListener.locationAvailable()
            }
            else {
                mLocationPermissionListener.locationUnavailable()
            }
        }
    }

    companion object {

        private val REQUEST_CODE = 99

        fun create(): Fragment {
            return MapLocationFragment()
        }
    }
}
