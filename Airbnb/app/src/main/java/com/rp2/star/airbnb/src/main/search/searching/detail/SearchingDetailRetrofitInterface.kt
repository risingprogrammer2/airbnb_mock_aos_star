package com.rp2.star.airbnb.src.main.search.searching.detail

import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.main.search.searching.models.GetImpossibleDatesResponse
import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeDetailResponse
import com.rp2.star.airbnb.src.main.search.searching.models.PostLodgeStoreBody
import retrofit2.Call
import retrofit2.http.*

interface SearchingDetailRetrofitInterface {

    // 클릭하면 상세정보 요청
    @GET("/lodging/{lodgeId}/info")
    fun getLodgeDetailRequest(
        //@Header("x-access-token") accessToken: String,
        @Header("access_token") accessToken: String,
        @Path("lodgeId") lodgeId: Int): Call<GetLodgeDetailResponse>

    // 숙소 찜, listName: 찜 폴더 이름
    @POST("/saves")
    fun postLodgeStoreRequest(@Body postLodgeStoreBody: PostLodgeStoreBody): Call<BaseResponse>

    // 숙소 찜 취소
    @DELETE("/saves/{lodgeId}")
    fun deleteLodgeStoreRequest(@Path("lodgeId") lodgeId: Int): Call<BaseResponse>

    // 예약 가능 날짜 확인
    @GET("/lodgings/{lodgingId}/calendars")
    fun getImpossibleDatesRequest(@Path("lodgingId") lodgeId: Int): Call<GetImpossibleDatesResponse>
}