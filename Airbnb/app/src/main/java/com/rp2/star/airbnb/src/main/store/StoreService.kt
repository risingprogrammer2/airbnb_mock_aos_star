package com.rp2.star.airbnb.src.main.store

import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.src.main.store.models.GetFoldersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreService(val view: StoreFragmentView) {


    // 저장 숙소 폴더 요청
    fun tryGetStoredFolders() {
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(
            StoreRetrofitInterface::class.java
        )

        val page = 0 // 불러올 페이지 수
        val num = 10    // 페이지 당 데이터 수

        storeRetrofitInterface.getStoredFoldersRequest(page, num)
            .enqueue(object : Callback<GetFoldersResponse> {

                override fun onResponse(
                    call: Call<GetFoldersResponse>,
                    response: Response<GetFoldersResponse>
                ) {
                    view.onGetFoldersSuccess(response.body() as GetFoldersResponse)
                }

                override fun onFailure(call: Call<GetFoldersResponse>, t: Throwable) {
                    view.onGetFoldersFailure(t.message ?: "통신 오류")
                }
            })
    }
}