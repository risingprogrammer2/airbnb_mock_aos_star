package com.rp2.star.airbnb.src.main.store.in_folder

import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.src.main.store.models.GetLodgesInFolderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InFolderService(val view: InFolderFragmentView) {


    // 저장 숙소 폴더 요청
    fun tryGetStoredFolders(folderName: String) {
        val inFolderRetrofitInterface = ApplicationClass.sRetrofit.create(
            InFolderRetrofitInterface::class.java
        )

        val page = 0 // 불러올 페이지 수
        val num = 10    // 페이지 당 데이터 수

        inFolderRetrofitInterface.getLodgesInFolderRequest(folderName, page, num)
            .enqueue(object : Callback<GetLodgesInFolderResponse> {

                override fun onResponse(
                    call: Call<GetLodgesInFolderResponse>,
                    response: Response<GetLodgesInFolderResponse>
                ) {
                    view.onGetFoldersSuccess(response.body() as GetLodgesInFolderResponse)
                }

                override fun onFailure(call: Call<GetLodgesInFolderResponse>, t: Throwable) {
                    view.onGetFoldersFailure(t.message ?: "통신 오류")
                }
            })
    }
}