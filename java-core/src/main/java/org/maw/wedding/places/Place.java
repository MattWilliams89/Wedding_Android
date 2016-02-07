package org.maw.wedding.places;

public class Place {

    public final String name;
    public final String id;
    public final Geometry geometry;

    public Place(String id, String name, Geometry geometry) {
        this.id = id;
        this.name = name;
        this.geometry = geometry;
    }
}
