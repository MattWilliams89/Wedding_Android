package org.maw.wedding.places;

public class Place {

    public final String place_id;
    public final Geometry geometry;

    public Place(String place_id, Geometry geometry) {
        this.place_id = place_id;
        this.geometry = geometry;
    }
}
