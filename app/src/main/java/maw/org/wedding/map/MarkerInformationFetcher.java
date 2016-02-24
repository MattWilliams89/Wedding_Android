package maw.org.wedding.map;

import android.content.Context;

import com.google.android.gms.maps.model.Marker;

import org.maw.wedding.fetching.FetcherListener;
import org.maw.wedding.fetching.PlaceDetailsFetcher;

import maw.org.wedding.R;
import maw.org.wedding.map.Fetching.PlacePhotoFetcher;
import places.Photo;
import places.PlaceDetails;

public class MarkerInformationFetcher {
    public void fetch(final Context context, final Marker marker, final MarkerViewController markerViewController) {

        final String apiKey = context.getResources().getString(R.string.google_server_key);

        new PlaceDetailsFetcher().fetchPlaceForId(marker.getSnippet(), apiKey, new FetcherListener<PlaceDetails>() {
            @Override
            public void onSuccess(PlaceDetails result) {

                final MarkerViewModel markerViewModel = new MarkerViewModel();
                markerViewModel.title = result.getName();
                markerViewModel.id = result.getPlace_id();
                markerViewModel.websiteUrl = result.getWebsite();
                markerViewModel.phoneNumber = result.getFormatted_phone_number();
                markerViewModel.address = result.getFormatted_address();
                markerViewModel.rating = result.getRating();
                markerViewController.updateViewModelForMarker(marker, markerViewModel);

                if (result.getPhotos() != null) {

                    for (Photo photo : result.getPhotos()) {
                        new PlacePhotoFetcher().fetchPhotoForReference(context, photo.getPhoto_reference(), apiKey, photo.getWidth(), new PhotoRequestListener() {
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
