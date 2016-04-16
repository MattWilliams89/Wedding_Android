package maw.org.wedding.map

import android.content.Context
import com.google.android.gms.maps.model.Marker
import maw.org.wedding.R
import maw.org.wedding.map.Fetching.PlacePhotoFetcher
import org.maw.wedding.fetching.FetcherListener
import org.maw.wedding.fetching.PlaceDetailsFetcher
import org.maw.wedding.places.PlaceDetails

class MarkerInformationFetcher {
    fun fetch(context: Context, marker: Marker, markerViewModelStore: MarkerViewModelStore) {

        val apiKey = context.resources.getString(R.string.google_server_key)

        PlaceDetailsFetcher().fetchPlaceForId(marker.snippet, apiKey, object : FetcherListener<PlaceDetails> {
            override fun onSuccess(result: PlaceDetails) {

                val markerViewModel = MarkerViewModel(result.place_id, result.name, arrayListOf(), result.website, result.formatted_address, result.rating, result.formatted_phone_number)
                markerViewModelStore.putOrUpdate(marker.id, markerViewModel)
                marker.showInfoWindow()

                if (result.photos != null) {

                    for (photo in result.photos!!) {
                        PlacePhotoFetcher().fetchPhotoForReference(context, photo.photo_reference, apiKey, photo.width, object : PhotoRequestListener {
                            override fun onSuccess(imageURL: String) {
                                markerViewModel.imageUrls.add(imageURL)
                                markerViewModelStore.putOrUpdate(marker.id, markerViewModel)
                                marker.showInfoWindow()
                            }

                            override fun onFailure() {

                            }
                        })
                    }
                }
            }

            override fun onFailure() {

            }
        })
    }
}
