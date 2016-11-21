package maw.org.wedding.map

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import maw.org.wedding.R
import maw.org.wedding.map.hotel.HotelInformationActivity
import org.maw.wedding.fetching.FetcherListener
import org.maw.wedding.fetching.NearbyServicesFetcher
import org.maw.wedding.model.Location
import org.maw.wedding.model.PlaceList

class MapController(val mContext: Context, val mLocationRequester: LocationRequester) : OnMapReadyCallback {

    val mMonastery = LatLng(53.4683672,-2.1881899)

    private fun fetchNearbyHotels(googleMap: GoogleMap) {
        val nearbyServicesFetcher = NearbyServicesFetcher()
        nearbyServicesFetcher.fetchNearbyHotels(Location(mMonastery.latitude, mMonastery.longitude),
                mContext.resources.getString(R.string.google_server_key),
                object : FetcherListener<PlaceList> {
                    override fun onSuccess(result: PlaceList) {
                        val builder = LatLngBounds.Builder()
                        if (result.results?.count()!! > 0) {
                            for (place in result.results!!) {
                                val marker = MarkerOptions().position(LatLng(place.geometry.location.lat, place.geometry.location.lng)).snippet(place.place_id).icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_marker_icon))
                                builder.include(marker.position)
                                googleMap.addMarker(marker)
                            }
                            val bounds = builder.build()

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 25))
                        }
                    }

                    override fun onFailure() {
                        Log.e("FAILURE", "fail")
                    }
                })
    }

    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.addMarker(MarkerOptions().position(mMonastery).title("Gorton Monastery"))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMonastery, 15f))

        googleMap.isTrafficEnabled = true

        mLocationRequester.enableCurrentLocation(object : LocationPermissionListener {
            override fun locationUnavailable() {
                googleMap.isMyLocationEnabled = true
            }

            override fun locationAvailable() {
                googleMap.isMyLocationEnabled = false
            }
        })

        val markerViewModelStore = MarkerViewModelStore()
        val markerViewAdapter = MarkerViewAdapter(mContext, markerViewModelStore)
        googleMap.setInfoWindowAdapter(markerViewAdapter)

        fetchNearbyHotels(googleMap)

        googleMap.setOnMarkerClickListener { marker ->
            if (marker.position != mMonastery) {
                if (!markerViewModelStore.exists(marker.id)) {
                    MarkerInformationFetcher().fetch(mContext, marker, markerViewModelStore)
                } else {
                    marker.showInfoWindow()
                }
            }
            false
        }

        googleMap.setOnInfoWindowClickListener { marker ->
            val i = Intent(mContext, HotelInformationActivity::class.java)
            i.putExtra(HotelInformationActivity.VIEW_MODEL, markerViewModelStore.get(marker.id))
            mContext.startActivity(i)
        }
    }
}
