package org.maw.wedding.fetching;

import org.maw.wedding.places.PlaceDetails;
import org.maw.wedding.places.PlaceDetailsWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class PlaceDetailsFetcher {

    public interface PlaceDetailsService {
        @GET("json")
        Call<PlaceDetailsWrapper> getNearby(@Query("placeid") String placeId, @Query("key") String apiKey);
    }

    public void fetchPlaceForId(String placeId, String apiKey, final FetcherListener<PlaceDetails> fetcherListener) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/details/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceDetailsService service = retrofit.create(PlaceDetailsService.class);

        service.getNearby(placeId, apiKey).enqueue(new Callback<PlaceDetailsWrapper>() {
            @Override
            public void onResponse(Call<PlaceDetailsWrapper> call, Response<PlaceDetailsWrapper> response) {
                fetcherListener.onSuccess(response.body().placeDetails);
            }

            @Override
            public void onFailure(Call<PlaceDetailsWrapper> call, Throwable t) {
                fetcherListener.onFailure();
            }
        });

    }
}
