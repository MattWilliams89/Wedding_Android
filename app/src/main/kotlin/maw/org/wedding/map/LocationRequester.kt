package maw.org.wedding.map

interface LocationRequester {
    fun enableCurrentLocation(locationPermissionListener: LocationPermissionListener)
}

