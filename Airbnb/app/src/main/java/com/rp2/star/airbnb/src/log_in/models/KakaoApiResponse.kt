package com.rp2.star.airbnb.src.log_in.models

import com.google.gson.annotations.SerializedName

data class KakaoApiResponse(
    @SerializedName("profile") val profile: KakaoProfileResponse,
    @SerializedName("email") var email: String? = null,
    @SerializedName("birthday") var birthday: String? = null
)