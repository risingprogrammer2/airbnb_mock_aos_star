package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class ResultLodgeDetailLodgeSpace(
    @SerializedName("bedroom_cnt") val bedroomCnt: Float,
    @SerializedName("bed_cnt") val bedCnt: Float,
    @SerializedName("bathroom_cnt") val bathroomCnt: Float
)