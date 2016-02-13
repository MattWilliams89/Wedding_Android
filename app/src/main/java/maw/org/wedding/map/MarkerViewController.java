package maw.org.wedding.map;

import com.google.android.gms.maps.model.Marker;

public interface MarkerViewController {
    void updateViewModelForMarker(Marker marker, MarkerViewModel markerViewModel);
    boolean markerHasData(String markerID);

    MarkerViewModel getMarkerViewModel(String id);
}
