package com.rp2.star.airbnb.src.logIn.models

import com.google.gson.annotations.SerializedName

// 인증번호 비교 요청
data class PostCertNumCompRequest(
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("code") val code: String    // 인증번호

)