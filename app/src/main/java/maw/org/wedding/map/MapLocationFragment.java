package maw.org.wedding.map;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import maw.org.wedding.R;


public class MapLocationFragment extends MapFragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {

    private static final int REQUEST_CODE = 99;

    private LatLng mMediaCity = new LatLng(53.472704, -2.298379);
    private GoogleMap mMap;

    public static Fragment create() {
        return new MapLocationFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(mMediaCity).title("MediaCityUk"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mMediaCity, 15));

        mMap.setTrafficEnabled(true);
        mMap.setOnMapLoadedCallback(this);
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = lm.getProviders(true);

            Location location = null;
            for (String provider : providers) {
                location = lm.getLastKnownLocation(provider);
                if (location != null)
                    break;
            }
            if (location != null) {
                LatLng positionLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(positionLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location)));

                LatLngBounds bounds = toBounds(mMediaCity, positionLatLng);
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 500));
            }
        }
        else {
            String[] permissionsArray = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            FragmentCompat.requestPermissions(this, permissionsArray, REQUEST_CODE);
        }
    }

    public LatLngBounds toBounds(LatLng positionA, LatLng positionB) {
        double latMin = Math.min(positionA.latitude, positionB.latitude);
        double latMax = Math.max(positionA.latitude, positionB.latitude);

        double  lngMin = Math.min(positionA.longitude, positionB.longitude);
        double lngMax = Math.max(positionA.longitude, positionB.longitude);

        LatLng sw = new LatLng(latMin, lngMin);
        LatLng ne = new LatLng(latMax, lngMax);
        return new LatLngBounds(sw, ne);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    @Override
    public void onMapLoaded() {
        getCurrentLocation();
    }
}
