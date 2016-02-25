package maw.org.wedding.map

import android.content.Context
import com.google.android.gms.maps.model.Marker
import maw.org.wedding.R
import maw.org.wedding.map.Fetching.PlacePhotoFetcher
import org.maw.wedding.fetching.FetcherListener
import org.maw.wedding.fetching.PlaceDetailsFetcher
import org.maw.wedding.places.PlaceDetails

class MarkerInformationFetcher {
    fun fetch(context: Context, marker: Marker, markerViewController: MarkerViewController) {

        val apiKey = context.resources.getString(R.string.google_server_key)

        PlaceDetailsFetcher().fetchPlaceForId(marker.snippet, apiKey, object : FetcherListener<PlaceDetails> {
            override fun onSuccess(result: PlaceDetails) {

                val markerViewModel = MarkerViewModel()
                markerViewModel.title = result.name
                markerViewModel.id = result.place_id
                markerViewModel.websiteUrl = result.website
                markerViewModel.phoneNumber = result.formatted_phone_number
                markerViewModel.address = result.formatted_address
                markerViewModel.rating = result.rating
                markerViewController.updateViewModelForMarker(marker, markerViewModel)

                if (result.photos != null) {

                    for (photo in result.photos!!) {
                        PlacePhotoFetcher().fetchPhotoForReference(context, photo.photo_reference, apiKey, photo.width, object : PhotoRequestListener {
                            override fun onSuccess(imageURL: String) {
                                markerViewModel.imageUrls.add(imageURL)
                                markerViewController.updateViewModelForMarker(marker, markerViewModel)
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
