package maw.org.wedding.map;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
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


public class MapLocationFragment extends MapFragment implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 99;

    private LatLng mMediaCity = new LatLng(53.472704, -2.298379);
    private GoogleMap mMap;
    private MarkerViewController mMarkerViewController;

    public static Fragment create() {
        return new MapLocationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(mMediaCity).title("MediaCityUk"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMediaCity, 15));

        mMap.setTrafficEnabled(true);
        enableCurrentLocation();

        mMarkerViewController = new HotelsInfoWindowAdapter(getContext());
        mMap.setInfoWindowAdapter((GoogleMap.InfoWindowAdapter)mMarkerViewController);

        NearbyServicesFetcher nearbyServicesFetcher = new NearbyServicesFetcher();
        nearbyServicesFetcher.fetchNearbyHotels(new org.maw.wedding.places.Location(mMediaCity.latitude, mMediaCity.longitude),
                getContext().getResources().getString(R.string.google_server_key),
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

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                if (!marker.getPosition().equals(mMediaCity)) {
                    if (!mMarkerViewController.markerHasData(marker.getId())) {
                        new MarkerInformationFetcher().fetch(getContext(), marker, mMarkerViewController);
                    }
                    else {
                        marker.showInfoWindow();
                    }
                }
                return false;
            }
        });
    }

    private void enableCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);
        }
        else {
            String[] permissionsArray = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            FragmentCompat.requestPermissions(this, permissionsArray, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                enableCurrentLocation();
            }
        }
    }
}
