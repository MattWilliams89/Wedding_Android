package maw.org.wedding.map;

import android.content.Context;

import com.google.android.gms.maps.model.Marker;

import org.maw.wedding.fetching.FetcherListener;
import org.maw.wedding.fetching.PlaceDetailsFetcher;
import org.maw.wedding.places.Photo;
import org.maw.wedding.places.PlaceDetails;

import maw.org.wedding.R;
import maw.org.wedding.map.Fetching.PlacePhotoFetcher;

public class MarkerInformationFetcher {
    public void fetch(final Context context, final Marker marker, final MarkerViewController markerViewController) {

        final String apiKey = context.getResources().getString(R.string.google_server_key);

        new PlaceDetailsFetcher().fetchPlaceForId(marker.getSnippet(), apiKey, new FetcherListener<PlaceDetails>() {
            @Override
            public void onSuccess(PlaceDetails result) {

                final MarkerViewModel markerViewModel = new MarkerViewModel();
                markerViewModel.title = result.name;
                markerViewModel.id = result.place_id;
                markerViewModel.websiteUrl = result.website;
                markerViewModel.phoneNumber = result.formatted_phone_number;
                markerViewModel.address = result.formatted_address;
                markerViewModel.rating = result.rating;
                markerViewController.updateViewModelForMarker(marker, markerViewModel);

                if (result.photos != null) {

                    for (Photo photo : result.photos) {
                        new PlacePhotoFetcher().fetchPhotoForReference(context, photo.photo_reference, apiKey, photo.width, new PhotoRequestListener() {
                            @Override
                            public void onSuccess(String imageURL) {
                                markerViewModel.imageUrls.add(imageURL);
                                markerViewController.updateViewModelForMarker(marker, markerViewModel);
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
