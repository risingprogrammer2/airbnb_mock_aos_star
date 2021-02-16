package com.rp2.star.airbnb.src.main.search.searching.detail

import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeDetailResponse
import com.rp2.star.airbnb.src.main.search.searching.models.PostLodgeStoreBody
import retrofit2.Call
import retrofit2.http.*

interface SearchingDetailRetrofitInterface {

    // 도시명으로 숙소들 정보 요청
    @GET("/lodging/{lodgeId}")
    fun getLodgeDetailRequest(
        //@Header("x-access-token") accessToken: String,
        @Header("access_token") accessToken: String,
        @Path("lodgeId") lodgeId: Int): Call<GetLodgeDetailResponse>

    // 숙소 찜, listName: 찜 폴더 이름
    @POST("/lodging/list")

    fun postLodgeStoreRequest(@Body postLodgeStoreBody: PostLodgeStoreBody): Call<BaseResponse>

    // 숙소 찜 취소
    @DELETE("/lodging/list/{lodgingId}")
    fun deleteLodgeStoreRequest(@Path("lodgingId") lodgeId: Int): Call<BaseResponse>
}