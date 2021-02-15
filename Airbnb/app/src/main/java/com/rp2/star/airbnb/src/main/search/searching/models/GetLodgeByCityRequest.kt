package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

// API 15 - 도시명으로 숙소 검색, GET의 Path parameter로 사용
data class GetLodgeByCityRequest(
    @SerializedName("cityName") val cityName: String
)