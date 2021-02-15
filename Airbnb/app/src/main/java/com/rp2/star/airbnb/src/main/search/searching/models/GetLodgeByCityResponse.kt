package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName
import com.rp2.star.airbnb.config.BaseResponse

data class GetLodgeByCityResponse(
    // @SerializedName("result") val result: ArrayList<ResultLodgeByCity>
    @SerializedName("lodge") val lodge: ArrayList<ResultLodgeByCity>
) : BaseResponse()