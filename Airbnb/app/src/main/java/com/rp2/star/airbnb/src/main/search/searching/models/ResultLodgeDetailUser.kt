package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class ResultLodgeDetailUser(
    @SerializedName("id") val id: Int,
    @SerializedName("superhost") val superhost: String,
    @SerializedName("name") val name: String,
    @SerializedName("city") val city: String,
    @SerializedName("lodging_type") val lodgingType: String,
    @SerializedName("place_type") val placeType: String,
    @SerializedName("area") val area: String,
    @SerializedName("capital") val capital: String,
    @SerializedName("profile_img") val hostImg: String,
    @SerializedName("total_user") val totalUser: Int,
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float,
    @SerializedName("rating") val rating: Float,
    @SerializedName("count") val count: Int,
    @SerializedName("accuracy_rating") val accuracyRating: Float,
    @SerializedName("communication_rating") val communicationRating: Float,
    @SerializedName("checkin_rating") val checkInRating: Float,
    @SerializedName("satisfaction_rating") val satisfactionRating: Float,
    @SerializedName("clean_rating") val cleanRating: Float,
    @SerializedName("position_rating") val positionRating: Float,

    /*@SerializedName("lodgeImages") val lodgeImages: ArrayList<String>,
    @SerializedName("lodgeSpaces") val lodgeSpaces: ResultLodgeByCitySpaces,
    @SerializedName("lodgeFacilities") val lodgeFacilities: ArrayList<String>
*/
)