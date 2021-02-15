package com.rp2.star.airbnb.src.main.search.searching.detail

import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SearchingDetailRetrofitInterface {

    // 도시명으로 숙소들 정보 요청
    @GET("/lodging/{lodgeId}")
    fun getLodgeDetailRequest(
        @Header("x-access-token") accessToken: String,
        @Path("lodgeId") lodgeId: Int): Call<GetLodgeDetailResponse>

    /*// 팀서버에 카카오 회원가입/로그인 요청
    @POST("/auth/kakao/user")
    fun postKakaoLogInRequest(@Body accessToken: PostKakaoLogInRequest): Call<SignUpResponse>*/
}