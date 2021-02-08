package com.rp2.star.airbnb.src.logIn.models

import com.google.gson.annotations.SerializedName

data class ResultSignUp(
    @SerializedName("token") val token: String
)