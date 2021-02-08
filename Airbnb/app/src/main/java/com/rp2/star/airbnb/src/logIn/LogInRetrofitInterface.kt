package com.rp2.star.airbnb.src.logIn

import com.rp2.star.airbnb.src.logIn.models.PostPhoneSignUpRequest
import com.rp2.star.airbnb.src.logIn.models.PostSocialSignUpRequest
import com.rp2.star.airbnb.src.logIn.models.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInRetrofitInterface {

    // 전화번호로 회원가입 요청
    @POST("/auth/new-user")
    fun postSignUp(@Body params: PostPhoneSignUpRequest): Call<SignUpResponse>

    // 소셜로 회원가입 요청
    @POST("/auth/new-user")
    fun postSignUp(@Body params: PostSocialSignUpRequest): Call<SignUpResponse>

    // 소셜로 로그인 요청 -> 회원가입 결과와 같은 값을 받는다(토큰 포함)
    @POST("/auth/social/user")
    fun postSignInSocial(@Body email: String): Call<SignUpResponse>
}