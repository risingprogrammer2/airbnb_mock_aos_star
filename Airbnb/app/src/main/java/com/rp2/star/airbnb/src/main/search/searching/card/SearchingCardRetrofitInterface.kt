package com.rp2.star.airbnb.src.main.search.searching.card

import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.main.search.searching.models.PostCardInfoBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchingCardRetrofitInterface {

    // 카드 정보 등록
    @POST("/users/cards/informs")
    fun postCardInfoRequest(@Body postCardInfoBody: PostCardInfoBody): Call<BaseResponse>
}