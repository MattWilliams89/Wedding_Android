package maw.org.wedding.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import maw.org.wedding.R
import java.util.*

class HotelsInfoWindowAdapter(context: Context) : GoogleMap.InfoWindowAdapter, MarkerViewController {

    private val mView: View

    private val mMarkerViewModelMap = HashMap<String, MarkerViewModel>()

    init {
        mView = LayoutInflater.from(context).inflate(R.layout.hotel_marker_layout, null)
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null

    }

    override fun getInfoContents(marker: Marker): View {

        val markerViewModel = mMarkerViewModelMap[marker.id]

        if (markerViewModel != null) {
            mView.findViewById(R.id.content).visibility = View.VISIBLE
            val hotelNameView = mView.findViewById(R.id.hotel_name) as TextView
            hotelNameView.text = markerViewModel.title

            val hotelImageView = mView.findViewById(R.id.hotel_image) as ImageView

            if (markerViewModel.imageUrls.size > 0) {
                Picasso.with(mView.context).load(markerViewModel.imageUrls[0]).into(hotelImageView)
            }
        } else {
            mView.findViewById(R.id.content).visibility = View.GONE
        }

        return mView
    }

    override fun updateViewModelForMarker(marker: Marker, markerViewModel: MarkerViewModel) {
        mMarkerViewModelMap.put(marker.id, markerViewModel)
        marker.showInfoWindow()
    }

    override fun markerHasData(markerID: String): Boolean {
        return mMarkerViewModelMap.containsKey(markerID)
    }

    override fun getMarkerViewModel(id: String): MarkerViewModel {
        return mMarkerViewModelMap[id]!!
    }
}
