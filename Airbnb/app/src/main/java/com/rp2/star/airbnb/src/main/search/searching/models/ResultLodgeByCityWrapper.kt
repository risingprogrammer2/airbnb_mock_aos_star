package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class ResultLodgeByCityWrapper(
    @SerializedName("lodge") val lodge: ArrayList<ResultLodgeByCity>,
)