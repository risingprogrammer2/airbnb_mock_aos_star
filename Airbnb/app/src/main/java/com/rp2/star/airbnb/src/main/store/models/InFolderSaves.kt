package com.rp2.star.airbnb.src.main.store.models

import com.google.gson.annotations.SerializedName

data class InFolderSaves (
    @SerializedName("id") val lodgeId: Int,                     // 숙소 아이디
    @SerializedName("superhost") val isSuperHost: String,
    @SerializedName("city") val city: String,                   //도시 이름
    @SerializedName("lodging_type") val lodgingType: String,    // 숙소 유형
    @SerializedName("place_type") val placeType: String,        // 장소 유형
    @SerializedName("total_user") val totalUser: Int,           // 최대 인원수
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float,
    @SerializedName("rating") val rating: Float,                // 별점
    @SerializedName("count") val ratingCount: Int,              // 리뷰 개수
    @SerializedName("folder") val folder: String,               // 폴더 이름
    @SerializedName("images") val images: ArrayList<String>,
)