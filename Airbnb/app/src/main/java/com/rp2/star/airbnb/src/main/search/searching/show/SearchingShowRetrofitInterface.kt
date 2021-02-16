package com.rp2.star.airbnb.src.main.search.searching.show

import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeByCityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchingShowRetrofitInterface {

    // 도시명으로 숙소들 정보 요청
    @GET("/lodging/city")
    fun getLodgeByCityRequest(
        //@Header("x-access-token") accessToken: String,
        @Header("access_token") accessToken: String,
        @Query("cityName") cityName: String): Call<GetLodgeByCityResponse>

    /*// 팀서버에 카카오 회원가입/로그인 요청
    @POST("/auth/kakao/user")
    fun postKakaoLogInRequest(@Body accessToken: PostKakaoLogInRequest): Call<SignUpResponse>*/
}