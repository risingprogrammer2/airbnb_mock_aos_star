package com.rp2.star.airbnb.src.log_in.models

import com.google.gson.annotations.SerializedName

data class NaverResponse(
    @SerializedName("resultCode") var resultCode: Int? = null,
    @SerializedName("message") val message: String,
    @SerializedName("response") var response: ResultSignUp? = null
)