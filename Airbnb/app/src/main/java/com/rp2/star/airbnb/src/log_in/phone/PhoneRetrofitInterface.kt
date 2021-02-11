package com.rp2.star.airbnb.src.log_in.phone

import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.log_in.models.PostCertNumCompRequest
import com.rp2.star.airbnb.src.log_in.models.PostCertNumRequest
import com.rp2.star.airbnb.src.log_in.models.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PhoneRetrofitInterface {

    // 인증번호 전송 요청
    @POST("/auth/sms")
    fun postCertNum(@Body phoneNumber: PostCertNumRequest): Call<BaseResponse>

    // 인증번호 비교 요청 - 번호로 로그인으로도 사용 가능
    @POST("/auth/sms-check")
    fun postCertNumComp(@Body params: PostCertNumCompRequest): Call<SignUpResponse>
}