package com.rp2.star.airbnb.src.main.search.searching.show

import android.util.Log
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeByCityResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchingShowService(val view: SearchingShowFragmentView) {

    // 도시명으로 숙소정보 요청
    fun tryGetLodgeByCity(cityName: String){

        //val getLodgeByCityRequest = GetLodgeByCityRequest(cityName)
        val accessToken = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
        val page = 1 // 불러올 페이지 수
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
}