package org.maw.wedding.fetching;

import org.maw.wedding.places.Location;
import org.maw.wedding.places.PlaceList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class PlacesRestAdapter {

    public interface NearbyHotelService {
        @GET("json")
        Call<PlaceList> getNearby(@Query("types") String types, @Query("location") String location, @Query("key") String apiKey, @Query("radius") String radius);
    }

    public void fetchNearbyHotels(Location loc, String apiKey, final FetcherListener<PlaceList> fetcherListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/radarsearch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NearbyHotelService service = retrofit.create(NearbyHotelService.class);

        String location = ""+ loc.lat + "," + loc.lng;

        Call<PlaceList> call = service.getNearby("lodging", location, apiKey, "1000");

        call.enqueue(new Callback<PlaceList>() {
            @Override
            public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
                fetcherListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {
                fetcherListener.onFailure();
            }
        });
    }
}
