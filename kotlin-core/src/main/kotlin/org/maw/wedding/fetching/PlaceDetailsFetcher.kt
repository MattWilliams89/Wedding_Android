package org.maw.wedding.fetching

import org.maw.wedding.model.PlaceDetails
import org.maw.wedding.model.PlaceDetailsWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class PlaceDetailsFetcher {

    interface PlaceDetailsService {
        @GET("json")
        fun getNearby(@Query("placeid") placeId: String, @Query("key") apiKey: String): Call<PlaceDetailsWrapper>
    }

    fun fetchPlaceForId(placeId: String, apiKey: String, fetcherListener: FetcherListener<PlaceDetails>) {

        val retrofit = Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/place/details/").addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(PlaceDetailsService::class.java)

        service.getNearby(placeId, apiKey).enqueue(object : Callback<PlaceDetailsWrapper> {
            override fun onResponse(call: Call<PlaceDetailsWrapper>, response: Response<PlaceDetailsWrapper>) {
                fetcherListener.onSuccess(response.body().placeDetails!!)
            }

            override fun onFailure(call: Call<PlaceDetailsWrapper>, t: Throwable) {
                fetcherListener.onFailure()
            }
        })

    }
}
