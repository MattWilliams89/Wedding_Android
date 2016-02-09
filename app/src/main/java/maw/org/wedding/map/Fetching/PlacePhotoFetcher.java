package maw.org.wedding.map.Fetching;

import android.content.Context;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PlacePhotoFetcher {


    public void fetchPhotoForReference(Context context, String photoReference, String apiKey, String maxWidth, final Marker marker) {

        String baseURL = "https://maps.googleapis.com/maps/api/place/photo?";

        Integer width = Integer.parseInt(maxWidth);

        String newWidth = String.valueOf(width/3);

        baseURL += "key=" + apiKey;
        baseURL += "&photoreference=" + photoReference;
        baseURL += "&maxwidth=" + newWidth;

        final String finalBaseURL = baseURL;
        Picasso.with(context).load(baseURL).fetch(new Callback() {
            @Override
            public void onSuccess() {
                marker.setSnippet(finalBaseURL);
                marker.showInfoWindow();
            }

            @Override
            public void onError() {

            }
        });

    }
}
