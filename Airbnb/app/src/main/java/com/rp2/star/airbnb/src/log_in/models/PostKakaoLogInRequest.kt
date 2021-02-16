package com.rp2.star.airbnb.src.log_in.models

import com.google.gson.annotations.SerializedName

// 인증번호 비교 요청
data class PostKakaoLogInRequest(
    @SerializedName("access_token") val accessToken: String
    // @SerializedName("x-access-token") val accessToken: String
)