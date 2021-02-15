package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

// API15 - 숙소정보 조회 - lodgeImages
data class ResultLodgeByCityImages(
    @SerializedName("lodgeImages") val lodgeImageList: ArrayList<String>
)