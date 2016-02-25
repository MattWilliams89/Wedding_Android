package org.maw.wedding.fetching

import org.maw.wedding.places.Location
import org.maw.wedding.places.PlaceList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class NearbyServicesFetcher {

    interface RadarSearchService {
        @GET("json")
        fun getNearby(@Query("types") types: String, @Query("location") location: String, @Query("key") apiKey: String, @Query("radius") radius: String): Call<PlaceList>
    }

    fun fetchNearbyHotels(loc: Location, apiKey: String, fetcherListener: FetcherListener<PlaceList>) {
        val retrofit = Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/place/radarsearch/").addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create<RadarSearchService>(RadarSearchService::class.java)

        val location = "" + loc.lat + "," + loc.lng

        val call = service.getNearby("lodging", location, apiKey, "1000")

        call.enqueue(object : Callback<PlaceList> {
            override fun onResponse(call: Call<PlaceList>, response: Response<PlaceList>) {
                fetcherListener.onSuccess(response.body())
            }

            override fun onFailure(call: Call<PlaceList>, t: Throwable) {
                fetcherListener.onFailure()
            }
        })
    }
}
