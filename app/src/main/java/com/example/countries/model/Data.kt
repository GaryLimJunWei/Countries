package com.example.countries.model

import com.google.gson.annotations.SerializedName

/*
    This is the data class for the API
 */

data class Country(
    @SerializedName("name")
    val countryName : String?,

    @SerializedName("capital")
    val capital : String?,

    @SerializedName("flagPNG")
    val flag:String?
)