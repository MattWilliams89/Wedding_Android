package maw.org.wedding.map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.maw.wedding.fetching.FetcherListener;
import org.maw.wedding.fetching.NearbyServicesFetcher;
import org.maw.wedding.places.Place;
import org.maw.wedding.places.PlaceList;

import maw.org.wedding.R;

public class MapController implements OnMapReadyCallback {

    private LatLng mMediaCity = new LatLng(53.472704, -2.298379);
    private GoogleMap mMap;
    private MarkerViewController mMarkerViewController;
    private Context mContext;
    private LocationRequester mLocationRequester;

    public MapController(Context context, LocationRequester locationRequester) {
        mContext = context;
        mLocationRequester = locationRequester;
    }

    private void fetchNearbyHotels() {
        NearbyServicesFetcher nearbyServicesFetcher = new NearbyServicesFetcher();
        nearbyServicesFetcher.fetchNearbyHotels(new org.maw.wedding.places.Location(mMediaCity.latitude, mMediaCity.longitude),
                mContext.getResources().getString(R.string.google_server_key),
                new FetcherListener<PlaceList>() {
                    @Override
                    public void onSuccess(PlaceList result) {
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (Place place : result.results) {
                            MarkerOptions marker = new MarkerOptions()
                                    .position(new LatLng(place.geometry.location.lat, place.geometry.location.lng))
                                    .snippet(place.place_id)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_marker_icon));
                            builder.include(marker.getPosition());
                            mMap.addMarker(marker);
                        }
                        LatLngBounds bounds = builder.build();

                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 25));
                    }

                    @Override
                    public void onFailure() {
                        Log.e("FAILURE", "fail");
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(mMediaCity).title("MediaCityUk"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMediaCity, 15));

        mMap.setTrafficEnabled(true);
        mLocationRequester.enableCurrentLocation();

        mMarkerViewController = new HotelsInfoWindowAdapter(mContext);
        mMap.setInfoWindowAdapter((GoogleMap.InfoWindowAdapter)mMarkerViewController);

        fetchNearbyHotels();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                if (!marker.getPosition().equals(mMediaCity)) {
                    if (!mMarkerViewController.markerHasData(marker.getId())) {
                        new MarkerInformationFetcher().fetch(mContext, marker, mMarkerViewController);
                    }
                    else {
                        marker.showInfoWindow();
                    }
                }
                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(marker.getSnippet()));
                mContext.startActivity(i);
            }
        });
    }

    public void setMyLocationEnabled(boolean locationEnabled) {
        try {
            mMap.setMyLocationEnabled(locationEnabled);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
