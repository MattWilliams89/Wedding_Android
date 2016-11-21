package org.maw.wedding.model

data class PlaceDetails(val place_id: String, val name: String, val website: String?, val rating: String?, val formattedAddress: String?, val formattedPhoneNumber: String?,
                        val photos: List<Photo> = listOf())

