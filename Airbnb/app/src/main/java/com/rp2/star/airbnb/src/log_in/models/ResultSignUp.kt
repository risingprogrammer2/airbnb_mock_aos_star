package com.rp2.star.airbnb.src.log_in.models

import com.google.gson.annotations.SerializedName

data class ResultSignUp(
    @SerializedName("id") var id: Int = -1,
    @SerializedName("name") var name: String? = null,
    @SerializedName("token") val token: String,
    @SerializedName("email") var email: String? = null
)