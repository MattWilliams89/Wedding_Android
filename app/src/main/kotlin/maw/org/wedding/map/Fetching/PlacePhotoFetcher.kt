package maw.org.wedding.map.Fetching

import android.content.Context

import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

import maw.org.wedding.map.PhotoRequestListener

class PlacePhotoFetcher {

    fun fetchPhotoForReference(context: Context, photoReference: String, apiKey: String, maxWidth: String, listener: PhotoRequestListener) {
        var baseURL = "https://maps.googleapis.com/maps/api/place/photo?"

        val width = Integer.parseInt(maxWidth)

        val newWidth = (width / 3).toString()

        baseURL += "key=" + apiKey
        baseURL += "&photoreference=" + photoReference
        baseURL += "&maxwidth=" + newWidth

        val finalBaseURL = baseURL
        Picasso.with(context).load(baseURL).fetch(object : Callback {
            override fun onSuccess() {
                listener.onSuccess(finalBaseURL)
            }

            override fun onError() {
                listener.onFailure()
            }
        })

    }
}
