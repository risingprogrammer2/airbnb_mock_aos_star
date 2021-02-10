package com.rp2.star.airbnb.src.log_in.models

import com.google.gson.annotations.SerializedName

// 회원가입 요청
data class PostPhoneSignUpRequest(
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("birthDay") val birthDay: String,
    @SerializedName("password") val password: String = "123456789"
)