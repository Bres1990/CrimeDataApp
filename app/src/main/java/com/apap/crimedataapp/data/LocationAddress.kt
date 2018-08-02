package com.apap.crimedataapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Adam on 2018-08-02.
 */
class LocationAddress {

    @SerializedName("village")
    private val _village: String? = null

    @SerializedName("county")
    private val _county: String? = null

    @SerializedName("state")
    private val _state: String? = null

    @SerializedName("postcode")
    private val _postCode: String? = null

    @SerializedName("country")
    private val _country: String? = null

    @SerializedName("country_code")
    private val _countryCode: String? = null

    override fun toString(): String {
        return _country!!
    }
}