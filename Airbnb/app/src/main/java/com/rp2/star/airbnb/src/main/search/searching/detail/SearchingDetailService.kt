package com.rp2.star.airbnb.src.main.search.searching.detail

import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.main.search.searching.models.GetImpossibleDatesResponse
import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeDetailResponse
import com.rp2.star.airbnb.src.main.search.searching.models.PostLodgeStoreBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchingDetailService(val view: SearchingDetailFragmentView) {

    val accessToken = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)


    // 숙소 상세 정보 요청
    fun tryGetLodgeDetail(lodgeId: Int){

        //val getLodgeByCityRequest = GetLodgeByCityRequest(cityName)

        val searchingDetailRetrofitInterface = ApplicationClass.sRetrofit.create(
            SearchingDetailRetrofitInterface::class.java)

        searchingDetailRetrofitInterface.getLodgeDetailRequest(accessToken!!, lodgeId)
            .enqueue(object : Callback<GetLodgeDetailResponse> {

                override fun onResponse(call: Call<GetLodgeDetailResponse>,
                                        response: Response<GetLodgeDetailResponse>) {
                    view.onGetLodgeDetailSuccess(response.body() as GetLodgeDetailResponse)
                }

                override fun onFailure(call: Call<GetLodgeDetailResponse>, t: Throwable) {
                    view.onGetLodgeDetailFailure(t.message ?: "통신 오류")
                }
            })
    }

    // 숙소 찜하기 요청
    fun tryPostStoreLodge(folderName: String, lodgeId: Int){

        val postLodgeStoreBody = PostLodgeStoreBody(folderName, lodgeId)

        val searchingDetailRetrofitInterface = ApplicationClass.sRetrofit.create(
            SearchingDetailRetrofitInterface::class.java)

        searchingDetailRetrofitInterface.postLodgeStoreRequest(postLodgeStoreBody)
            .enqueue(object : Callback<BaseResponse> {

                override fun onResponse(call: Call<BaseResponse>,
                                        response: Response<BaseResponse>) {
                    view.onPostLodgeStoreSuccess(response.body() as BaseResponse)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onPostLodgeStoreFailure(t.message ?: "통신 오류")
                }
            })
    }

    // 숙소 찜하기 취소 요청
    fun tryDeleteLodgeStore(lodgeId: Int){

        val searchingDetailRetrofitInterface = ApplicationClass.sRetrofit.create(
            SearchingDetailRetrofitInterface::class.java)

        searchingDetailRetrofitInterface.deleteLodgeStoreRequest(lodgeId)
            .enqueue(object : Callback<BaseResponse> {

                override fun onResponse(call: Call<BaseResponse>,
                                        response: Response<BaseResponse>) {
                    view.onDeleteLodgeStoreSuccess(response.body() as BaseResponse)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onDeleteLodgeStoreFailure(t.message ?: "통신 오류")
                }
            })
    }

    // 예약 불가능 날짜 요청
    fun tryGetImpossibleDates(lodgeId: Int){

        val searchingDetailRetrofitInterface = ApplicationClass.sRetrofit.create(
            SearchingDetailRetrofitInterface::class.java)

        searchingDetailRetrofitInterface.getImpossibleDatesRequest(lodgeId)
            .enqueue(object : Callback<GetImpossibleDatesResponse> {

                override fun onResponse(call: Call<GetImpossibleDatesResponse>,
                                        response: Response<GetImpossibleDatesResponse>) {
                    view.onGetImpossibleDatesSuccess(response.body() as GetImpossibleDatesResponse)
                }

                override fun onFailure(call: Call<GetImpossibleDatesResponse>, t: Throwable) {
                    view.onGetImpossibleDatesFailure(t.message ?: "통신 오류")
                }
            })
    }
}