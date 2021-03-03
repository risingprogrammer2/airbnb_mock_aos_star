package com.rp2.star.airbnb.src.main.store.in_folder

import com.rp2.star.airbnb.src.main.store.models.GetLodgesInFolderResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InFolderRetrofitInterface {

    // 저장 목록의 폴더 요청
    @GET("/saves/{saveName}")
    fun getLodgesInFolderRequest(@Path("saveName") folderName: String,
                                @Query("page") page: Int,
                                @Query("num") num: Int
    ): Call<GetLodgesInFolderResponse>
}