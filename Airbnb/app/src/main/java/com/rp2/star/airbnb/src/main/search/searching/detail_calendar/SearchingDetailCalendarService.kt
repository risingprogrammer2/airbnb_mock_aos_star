package com.rp2.star.airbnb.src.main.search.searching.detail_calendar

import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.src.main.search.searching.models.GetImpossibleDatesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchingDetailCalendarService(val view: SearchingDetailCalendarFragmentView) {

    val accessToken = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

    // 예약 불가능 날짜 요청
    fun tryGetImpossibleDates(lodgeId: Int){

        val searchingDetailCalendarRetrofitInterface = ApplicationClass.sRetrofit.create(
            SearchingDetailCalendarRetrofitInterface::class.java)

        searchingDetailCalendarRetrofitInterface.getImpossibleDatesRequest(lodgeId)
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