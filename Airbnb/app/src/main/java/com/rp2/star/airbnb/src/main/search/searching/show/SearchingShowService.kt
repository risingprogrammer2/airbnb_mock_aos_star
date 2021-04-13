package com.rp2.star.airbnb.src.main.search.searching.show

import android.util.Log
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeByCityResponse
import com.rp2.star.airbnb.src.main.search.searching.models.PostLodgeStoreBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchingShowService(val view: SearchingShowFragmentView) {

    // 도시명으로 숙소정보 요청
    fun tryGetLodgeByCity(cityName: String){

        //val getLodgeByCityRequest = GetLodgeByCityRequest(cityName)
        val accessToken = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
        val page = 0 // 불러올 페이지 수
        val num = 10    // 페이지 당 데이터 수

        val searchingShowRetrofitInterface = ApplicationClass.sRetrofit.create(
            SearchingShowRetrofitInterface::class.java)

        Log.d("로그","뭐냐고!!!!! accessToken: $accessToken, cityName: $cityName")
        searchingShowRetrofitInterface.getLodgeByCityRequest(accessToken!!, cityName, page, num)
            .enqueue(object : Callback<GetLodgeByCityResponse> {

                override fun onResponse(call: Call<GetLodgeByCityResponse>, response: Response<GetLodgeByCityResponse>) {
                    view.onGetLodgeSuccess(response.body() as GetLodgeByCityResponse)
                }

                override fun onFailure(call: Call<GetLodgeByCityResponse>, t: Throwable) {
                    view.onGetLodgeFailure(t.message ?: "통신 오류")
                }
            })
    }

    // 숙소 찜하기 요청
    fun tryPostStoreLodge(folderName: String, lodgeId: Int, pos: Int){

        val postLodgeStoreBody = PostLodgeStoreBody(folderName, lodgeId)

        val searchingShowRetrofitInterface = ApplicationClass.sRetrofit.create(
            SearchingShowRetrofitInterface::class.java)

        searchingShowRetrofitInterface.postLodgeStoreRequest(postLodgeStoreBody)
            .enqueue(object : Callback<BaseResponse> {

                override fun onResponse(call: Call<BaseResponse>,
                                        response: Response<BaseResponse>) {
                    view.onPostLodgeStoreSuccess(response.body() as BaseResponse, pos, folderName)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onPostLodgeStoreFailure(t.message ?: "통신 오류")
                }
            })
    }

    // 숙소 찜하기 취소 요청
    fun tryDeleteLodgeStore(lodgeId: Int, pos: Int, folderName: String){

        val searchingShowRetrofitInterface = ApplicationClass.sRetrofit.create(
            SearchingShowRetrofitInterface::class.java)

        searchingShowRetrofitInterface.deleteLodgeStoreRequest(lodgeId)
            .enqueue(object : Callback<BaseResponse> {

                override fun onResponse(call: Call<BaseResponse>,
                                        response: Response<BaseResponse>) {
                    view.onDeleteLodgeStoreSuccess(response.body() as BaseResponse, pos, folderName)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onDeleteLodgeStoreFailure(t.message ?: "통신 오류")
                }
            })
    }

    // 도시명으로 숙소정보 요청
    fun tryGetLodgeByCityDates(cityName: String){

        val accessToken = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
        val page = 0 // 불러올 페이지 수
        val num = 10    // 페이지 당 데이터 수

        val searchingShowRetrofitInterface = ApplicationClass.sRetrofit.create(
            SearchingShowRetrofitInterface::class.java)

        searchingShowRetrofitInterface.getLodgeByCityDatesRequest(accessToken!!, cityName, page, num,
        "2021-02-24", "2021-02-25")
            .enqueue(object : Callback<GetLodgeByCityResponse> {

                override fun onResponse(call: Call<GetLodgeByCityResponse>, response: Response<GetLodgeByCityResponse>) {
                    view.onGetLodgeSuccess(response.body() as GetLodgeByCityResponse)
                }

                override fun onFailure(call: Call<GetLodgeByCityResponse>, t: Throwable) {
                    view.onGetLodgeFailure(t.message ?: "통신 오류")
                }
            })
    }
}