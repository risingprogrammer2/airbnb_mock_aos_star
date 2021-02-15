package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class ResultLodgeDetailLodgeImges(
    @SerializedName("id") val id: Int,
    @SerializedName("img") val lodgeImg: String,


    /*@SerializedName("lodgeImages") val lodgeImages: ArrayList<String>,
    @SerializedName("lodgeSpaces") val lodgeSpaces: ResultLodgeByCitySpaces,
    @SerializedName("lodgeFacilities") val lodgeFacilities: ArrayList<String>
*/
)