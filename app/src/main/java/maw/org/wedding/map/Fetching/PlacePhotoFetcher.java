package maw.org.wedding.map.Fetching;

import android.content.Context;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import maw.org.wedding.map.PhotoRequestListener;

public class PlacePhotoFetcher {

    public void fetchPhotoForReference(Context context, String photoReference, String apiKey, String maxWidth, final PhotoRequestListener listener) {
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
                listener.onSuccess(finalBaseURL);
            }

            @Override
            public void onError() {
                listener.onFailure();
            }
        });

    }
}
