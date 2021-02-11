package com.rp2.star.airbnb.src.log_in.input_profile

import com.rp2.star.airbnb.src.log_in.models.PostPhoneSignUpRequest
import com.rp2.star.airbnb.src.log_in.models.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface InputProfileRetrofitInterface {

    // (번호로) 회원가입 요청
    @POST("/auth/user")
    fun postSignUp(@Body params: PostPhoneSignUpRequest): Call<SignUpResponse>

}