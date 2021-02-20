package com.rp2.star.airbnb.src.main.store

import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeByCityResponse
import com.rp2.star.airbnb.src.main.search.searching.models.PostLodgeStoreBody
import retrofit2.Call
import retrofit2.http.*

interface StoreRetrofitInterface {

    // 도시명으로 숙소들 정보 요청
    /*@GET("/lodging/city")
    fun getLodgeByCityRequest(
        //@Header("x-access-token") accessToken: String,
        @Header("access_token") accessToken: String,
        @Query("cityName") cityName: String): Call<GetLodgeByCityResponse>*/

    // 도시로만 검색
    @GET("/lodging")
    fun getLodgeByCityRequest(
        //@Header("x-access-token") accessToken: String,
        @Header("access_token") accessToken: String, // 필수
        @Query("location") location: String,    // 장소 검색어 - 필수
        @Query("page") page: Int,       // 페이지 숫자 - 필수
        @Query("num") num: Int         // 한 페이지당 - 필수
    ): Call<GetLodgeByCityResponse>

    // 총 게스트 수 포함하여 검색
    @GET("/lodging")
    fun getLodgeByCityGuestRequest(
        //@Header("x-access-token") accessToken: String,
        @Header("access_token") accessToken: String, // 필수
        @Query("location") location: String,    // 장소 검색어 - 필수
        @Query("page") page: Int,       // 페이지 숫자 - 필수
        @Query("num") num: Int,         // 한 페이지당 - 필수
        @Query("totalUser") totalUser: Int // 게스트 수
    ): Call<GetLodgeByCityResponse>

    // 날짜 범위 포함하여 검색
    @GET("/lodging")
    fun getLodgeByCityDatesRequest(
        //@Header("x-access-token") accessToken: String,
        @Header("access_token") accessToken: String, // 필수
        @Query("location") location: String,    // 장소 검색어 - 필수
        @Query("page") page: Int,       // 페이지 숫자 - 필수
        @Query("num") num: Int,         // 한 페이지당 - 필수
        @Query("start") start: String,  // 시작 날짜
        @Query("end") endDate: String   // 끝 날짜
    ): Call<GetLodgeByCityResponse>

    // 도시,날짜,게스트 수 포함해서 검색
    @GET("/lodging")
    fun getLodgeByCityAllRequest(
        //@Header("x-access-token") accessToken: String,
        @Header("access_token") accessToken: String, // 필수
        @Query("location") location: String,    // 장소 검색어 - 필수
        @Query("page") page: Int,       // 페이지 숫자 - 필수
        @Query("num") num: Int,         // 한 페이지당 - 필수
        @Query("start") start: String,  // 시작 날짜
        @Query("end") endDate: String,  // 끝
        @Query("totalUser") totalUser: Int // 게스트 수
    ): Call<GetLodgeByCityResponse>

    // 숙소 찜, listName: 찜 폴더 이름
    @POST("/lodging/list")
    fun postLodgeStoreRequest(@Body postLodgeStoreBody: PostLodgeStoreBody): Call<BaseResponse>

    // 숙소 찜 취소
    @DELETE("/lodging/list/{lodgingId}")
    fun deleteLodgeStoreRequest(@Path("lodgingId") lodgeId: Int): Call<BaseResponse>
}