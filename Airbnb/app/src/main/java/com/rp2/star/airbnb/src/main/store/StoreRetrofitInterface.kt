package com.rp2.star.airbnb.src.main.store

import com.rp2.star.airbnb.src.main.store.models.GetFoldersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreRetrofitInterface {

    // 저장 목록의 폴더 요청
    @GET("/saves")
    fun getStoredFoldersRequest(@Query("page") page: Int,
                                @Query("num") num: Int
    ): Call<GetFoldersResponse>
}