package com.rp2.star.airbnb.src.main.models

import com.google.gson.annotations.SerializedName
import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.log_in.models.ResultSignUp

data class ProfileResponse(
    // 베이스 리스폰스를 상속 받았으므로, 아래 내용은 포함이 되었습니다.
    // @SerializedName("success") val isSuccess: Boolean,
    // @SerializedName("code") val code: Int,
    // @SerializedName("message") val message: String,

    // 한번 등록 후, 전화번호 로그인할 때 사용
    @SerializedName("result") var result: ResultSignUp? = null
) : BaseResponse()