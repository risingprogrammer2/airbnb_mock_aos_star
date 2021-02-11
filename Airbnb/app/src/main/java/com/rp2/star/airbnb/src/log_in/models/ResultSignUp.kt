package com.rp2.star.airbnb.src.log_in.models

import com.google.gson.annotations.SerializedName

data class ResultSignUp(
    @SerializedName("token") val token: String,
    @SerializedName("id") val id: Int
)