package com.apap.crimedataapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Adam on 2018-08-02.
 */

class LocationDetails {

    @SerializedName("place_id")
    private val _placeId: String? = null

    @SerializedName("licence")
    private val _license: String? = null

    @SerializedName("osm_type")
    private val _osmType: String? = null

    @SerializedName("osm_id")
    private val _osmId: String? = null

    @SerializedName("lat")
    private val _lat: String? = null

    @SerializedName("lon")
    private val _lon: String? = null

    @SerializedName("display_name")
    private val _displayName: String? = null

    @SerializedName("address")
    private val _address: LocationAddress? = null

    @SerializedName("bounding_box")
    private val _boundingBox: String? = null

    override fun toString(): String {
        return _address.toString()
    }
}