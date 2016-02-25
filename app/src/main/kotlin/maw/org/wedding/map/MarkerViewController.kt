package maw.org.wedding.map

import com.google.android.gms.maps.model.Marker

interface MarkerViewController {
    fun updateViewModelForMarker(marker: Marker, markerViewModel: MarkerViewModel)
    fun markerHasData(markerID: String): Boolean

    fun getMarkerViewModel(id: String): MarkerViewModel
}
