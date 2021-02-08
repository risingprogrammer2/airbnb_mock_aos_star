package com.rp2.star.airbnb.src.logIn.models

import com.google.gson.annotations.SerializedName

// 회원가입 요청
data class PostSocialSignUpRequest(
    @SerializedName("email") val email: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("birthDay") val birthDay: String
)