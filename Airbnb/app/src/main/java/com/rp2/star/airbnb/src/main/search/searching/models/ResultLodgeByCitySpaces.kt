package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

// API15 - 숙소정보 조회 - lodgeCategories
data class ResultLodgeByCitySpaces(
    @SerializedName("bedroom_cnt") val bedroomCnt: Float,
    @SerializedName("bed_cnt") val bedCnt: Float,
    @SerializedName("bathroom_cnt") val bathroomCnt: Float
)