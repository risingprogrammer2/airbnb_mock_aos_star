package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class NoDatesByHostItem(
    @SerializedName("NotRefundable_start") val startDates: String,
    @SerializedName("NotRefundable_end") val endDates: String
)