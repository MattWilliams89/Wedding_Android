package maw.org.wedding.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import maw.org.wedding.R;

public class HotelsInfoWindowAdapter implements GoogleMap.InfoWindowAdapter, MarkerViewController {

    private View mView;

    private Map<String, MarkerViewModel> mMarkerViewModelMap = new HashMap<>();

    public HotelsInfoWindowAdapter(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.hotel_marker_layout, null);
    }

    @Override
    public View getInfoWindow(final Marker marker) {

        MarkerViewModel markerViewModel = mMarkerViewModelMap.get(marker.getId());

        if (markerViewModel != null) {
            ButterKnife.findById(mView, R.id.content).setVisibility(View.VISIBLE);
            TextView hotelNameView = ButterKnife.findById(mView, R.id.hotel_name);
            hotelNameView.setText(markerViewModel.title);

            ImageView hotelImageView = ButterKnife.findById(mView, R.id.hotel_image);

            Picasso.with(mView.getContext()).load(markerViewModel.imageUrl).into(hotelImageView);
        }
        else {
            ButterKnife.findById(mView, R.id.content).setVisibility(View.GONE);
        }

        return mView;
    }

    @Override
    public View getInfoContents(Marker marker) {

        MarkerViewModel markerViewModel = mMarkerViewModelMap.get(marker.getId());

        if (markerViewModel != null) {
            ButterKnife.findById(mView, R.id.content).setVisibility(View.VISIBLE);
            TextView hotelNameView = ButterKnife.findById(mView, R.id.hotel_name);
            hotelNameView.setText(markerViewModel.title);

            ImageView hotelImageView = ButterKnife.findById(mView, R.id.hotel_image);

            Picasso.with(mView.getContext()).load(markerViewModel.imageUrl).into(hotelImageView);
        }
        else {
            ButterKnife.findById(mView, R.id.content).setVisibility(View.GONE);
        }

        return mView;
    }

    @Override
    public void updateViewModelForMarker(Marker marker, MarkerViewModel markerViewModel) {
        mMarkerViewModelMap.put(marker.getId(), markerViewModel);
        marker.showInfoWindow();
    }

    @Override
    public boolean markerHasData(String markerID) {
        return mMarkerViewModelMap.containsKey(markerID);
    }
}
