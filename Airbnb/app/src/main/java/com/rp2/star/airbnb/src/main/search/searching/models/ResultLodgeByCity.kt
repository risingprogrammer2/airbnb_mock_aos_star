package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class ResultLodgeByCity(
    @SerializedName("id") val id: Int,
    @SerializedName("superhost") val superhost: String,
    @SerializedName("name") val name: String,
    @SerializedName("city") val city: String,
    @SerializedName("lodging_type") val lodgingType: String,
    @SerializedName("place_type") val placeType: String,
    @SerializedName("total_user") val totalUser: Int,
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float,
    @SerializedName("rating") val rating: Float?,
    @SerializedName("count") val count: Int,
    @SerializedName("isSave") val isSave: Boolean = false,
    @SerializedName("lodgeImages") val lodgeImages: ArrayList<String>,
    @SerializedName("lodgeSpaces") val lodgeSpaces: ResultLodgeByCitySpaces,
    @SerializedName("lodgeFacilities") val lodgeFacilities: ArrayList<String>,
    @SerializedName("price") val price: Int? = -1,    // 1박 당
    @SerializedName("total_price") val totalPrice: Int? = -1,    // 총 가격
)