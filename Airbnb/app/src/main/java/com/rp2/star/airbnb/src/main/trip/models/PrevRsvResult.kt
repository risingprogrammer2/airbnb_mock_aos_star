package com.rp2.star.airbnb.src.main.trip.models

import com.google.gson.annotations.SerializedName

data class PrevRsvResult (
    @SerializedName("start") val startDates: String,
    @SerializedName("end") val endDates: String,
    @SerializedName("name") val lodgingName: String,
    // @SerializedName("lodging_id") val lodgingId: Int,
    @SerializedName("lodging_img") val lodgingImg: String,
    @SerializedName("capital") val lodgingCapital: String,
    @SerializedName("reservation_id") val reservationId: Int,
    //@SerializedName("status") val rsvStatus: String         // "O","X"
)