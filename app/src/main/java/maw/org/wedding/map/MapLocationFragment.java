package maw.org.wedding.map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapLocationFragment extends MapFragment implements OnMapReadyCallback{

    private GoogleMap mMap;

    public static Fragment create() {
        return new MapLocationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getMapAsync(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng home = new LatLng(53.472704, -2.298379);
        mMap.addMarker(new MarkerOptions().position(home).title("MediaCityUk"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 15));

        mMap.setTrafficEnabled(true);
    }
}
