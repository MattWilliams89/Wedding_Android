package maw.org.wedding.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import butterknife.ButterKnife;
import maw.org.wedding.R;

public class HotelsInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private View mView;

    public HotelsInfoWindowAdapter(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.hotel_marker_layout, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView hotelNameView = ButterKnife.findById(mView, R.id.hotel_name);

        hotelNameView.setText(marker.getTitle());

        return mView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView hotelNameView = ButterKnife.findById(mView, R.id.hotel_name);

        hotelNameView.setText(marker.getTitle());

        return mView;
    }
}
