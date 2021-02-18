package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class PostReserveBody (
    @SerializedName("start") val startDate: String,
    @SerializedName("end") val endDate: String,
    @SerializedName("headcount") val headCount: String,
    @SerializedName("message") val message: String,
    @SerializedName("payment_id") val paymentId: Int,
    @SerializedName("coupon_id") var couponId: Int = 1,
    @SerializedName("lodging_id") val lodgingId: Int
)