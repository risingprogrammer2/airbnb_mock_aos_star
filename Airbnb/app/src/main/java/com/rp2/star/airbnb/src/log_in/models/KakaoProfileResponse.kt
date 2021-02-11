package com.rp2.star.airbnb.src.log_in.models

import com.google.gson.annotations.SerializedName

data class KakaoProfileResponse(
    @SerializedName("nickname") var nickname: String,
    @SerializedName("profile_image") var profile_image: String? = null,
    @SerializedName("thumbnail_image_url") var thumbnail_image_url: String? = null,
    @SerializedName("profile_needs_agreement") var profile_needs_agreement: Boolean = true
)