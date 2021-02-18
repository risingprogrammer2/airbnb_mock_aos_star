package com.rp2.star.airbnb.src.main.search.searching.pay

import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.main.search.searching.models.PostReserveBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchingPayRetrofitInterface {

    // 카드 정보 등록
    @POST("/lodgings/reservations")
    fun postReserveRequest(@Body postReserveBody: PostReserveBody): Call<BaseResponse>
}