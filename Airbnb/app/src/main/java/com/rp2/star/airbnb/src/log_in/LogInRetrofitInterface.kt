package com.rp2.star.airbnb.src.log_in

import com.rp2.star.airbnb.src.log_in.models.PostKakaoLogInRequest
import com.rp2.star.airbnb.src.log_in.models.PostPhoneSignUpRequest
import com.rp2.star.airbnb.src.log_in.models.PostSocialSignUpRequest
import com.rp2.star.airbnb.src.log_in.models.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInRetrofitInterface {

    // 팀서버에 카카오 회원가입/로그인 요청
    @POST("/auth/kakao/user")
    fun postKakaoLogInRequest(@Body accessToken: PostKakaoLogInRequest): Call<SignUpResponse>

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