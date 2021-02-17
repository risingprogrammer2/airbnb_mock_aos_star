package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class ResultLodgeDetailLodgeSpace(
    @SerializedName("id") val id: Int,
    @SerializedName("category") val category: String,
    @SerializedName("amount") val amount: Float,
    @SerializedName("name") val name: String
)