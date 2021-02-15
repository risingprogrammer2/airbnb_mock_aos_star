package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class ResultLodgeDetailLodgeProperty(
    @SerializedName("property") val property: String,
    @SerializedName("property_img") val img: String,
    @SerializedName("property_detail") val detail: String
)