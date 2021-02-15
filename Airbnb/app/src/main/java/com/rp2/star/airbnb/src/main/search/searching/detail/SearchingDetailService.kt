package com.rp2.star.airbnb.src.main.search.searching.detail

import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchingDetailService(val view: SearchingDetailFragmentView) {

    private val accessToken = ApplicationClass.sSharedPreferences.getString("jwt", null)

    // 숙소 상세 정보 요청
    fun tryGetLodgeDetail(lodgeId: Int){

        //val getLodgeByCityRequest = GetLodgeByCityRequest(cityName)

        val searchingDetailRetrofitInterface = ApplicationClass.sRetrofit.create(
            SearchingDetailRetrofitInterface::class.java)

        searchingDetailRetrofitInterface.getLodgeDetailRequest(accessToken!!, lodgeId)
            .enqueue(object : Callback<GetLodgeDetailResponse> {

                override fun onResponse(call: Call<GetLodgeDetailResponse>, response: Response<GetLodgeDetailResponse>) {
                    view.onGetLodgeDetailSuccess(response.body() as GetLodgeDetailResponse)
                }

                override fun onFailure(call: Call<GetLodgeDetailResponse>, t: Throwable) {
                    view.onGetLodgeDetailFailure(t.message ?: "통신 오류")
                }
            })
    }
}