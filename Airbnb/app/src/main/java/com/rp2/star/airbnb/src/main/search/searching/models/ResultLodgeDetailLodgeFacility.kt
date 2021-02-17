package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class ResultLodgeDetailLodgeFacility(
    //@SerializedName("id") val id: Int,
    @SerializedName("facility_img") val img: String,
    @SerializedName("name") val name: String
)