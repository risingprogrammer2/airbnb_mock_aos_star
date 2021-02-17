package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class NoDatesByGuestsItem(
    @SerializedName("Reservation_start") val startDates: String,
    @SerializedName("Reservation_end") val endDates: String

)