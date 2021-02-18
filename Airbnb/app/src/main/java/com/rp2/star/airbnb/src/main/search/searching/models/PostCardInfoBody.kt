package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class PostCardInfoBody (
    @SerializedName("userId") val userId: Int,
    @SerializedName("credit_number") val creditNumber: String,
    @SerializedName("credit_period") val creditPeriod: String,
    @SerializedName("credit_CVV") val creditCVV: String,
    @SerializedName("postcode") val postalCode: String?,
)