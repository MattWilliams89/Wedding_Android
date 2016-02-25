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
import org.maw.wedding.places.Location
import org.maw.wedding.places.PlaceList

class MapController(private val mContext: Context, private val mLocationRequester: LocationRequester) : OnMapReadyCallback {

    private val mMediaCity = LatLng(53.472704, -2.298379)
    private var mMap: GoogleMap? = null
    private var mMarkerViewController: MarkerViewController? = null

    private fun fetchNearbyHotels() {
        val nearbyServicesFetcher = NearbyServicesFetcher()
        nearbyServicesFetcher.fetchNearbyHotels(Location(mMediaCity.latitude, mMediaCity.longitude),
                mContext.resources.getString(R.string.google_server_key),
                object : FetcherListener<PlaceList> {
                    override fun onSuccess(result: PlaceList) {
                        val builder = LatLngBounds.Builder()
                        for (place in result.results!!) {
                            val marker = MarkerOptions().position(LatLng(place.geometry.location.lat, place.geometry.location.lng)).snippet(place.place_id).icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_marker_icon))
                            builder.include(marker.position)
                            mMap!!.addMarker(marker)
                        }
                        val bounds = builder.build()

                        mMap!!.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 25))
                    }

                    override fun onFailure() {
                        Log.e("FAILURE", "fail")
                    }
                })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap!!.addMarker(MarkerOptions().position(mMediaCity).title("MediaCityUk"))
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(mMediaCity, 15f))

        mMap!!.isTrafficEnabled = true
        mLocationRequester.enableCurrentLocation()

        mMarkerViewController = HotelsInfoWindowAdapter(mContext)
        mMap!!.setInfoWindowAdapter(mMarkerViewController as GoogleMap.InfoWindowAdapter?)

        fetchNearbyHotels()

        mMap!!.setOnMarkerClickListener { marker ->
            if (marker.position != mMediaCity) {
                if (!mMarkerViewController!!.markerHasData(marker.id)) {
                    MarkerInformationFetcher().fetch(mContext, marker, mMarkerViewController as HotelsInfoWindowAdapter)
                } else {
                    marker.showInfoWindow()
                }
            }
            false
        }

        mMap!!.setOnInfoWindowClickListener { marker ->
            val i = Intent(mContext, HotelInformationActivity::class.java)
            i.putExtra(HotelInformationActivity.VIEW_MODEL, mMarkerViewController!!.getMarkerViewModel(marker.id))
            mContext.startActivity(i)
        }
    }

    fun setMyLocationEnabled(locationEnabled: Boolean) {
        try {
            mMap!!.isMyLocationEnabled = locationEnabled
        } catch (e: SecurityException) {
            e.printStackTrace()
        }

    }
}
