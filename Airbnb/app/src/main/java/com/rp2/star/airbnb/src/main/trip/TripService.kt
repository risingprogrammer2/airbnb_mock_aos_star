package com.rp2.star.airbnb.src.main.trip

import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.src.main.trip.models.GetScheduleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TripService(val view:TripFragmentView) {

    val accessToken = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)


    // 이전 예약 정보 요청
    fun tryGetPrevReserve(){

        val tripRetrofitInterface = ApplicationClass.sRetrofit.create(
            TripRetrofitInterface::class.java)

        tripRetrofitInterface.getPrevReserveRequest()
            .enqueue(object : Callback<GetScheduleResponse> {

                override fun onResponse(call: Call<GetScheduleResponse>,
                                        response: Response<GetScheduleResponse>) {
                    view.onGetPrevRsvSuccess(response.body() as GetScheduleResponse)
                }

                override fun onFailure(call: Call<GetScheduleResponse>, t: Throwable) {
                    view.onGetPrevRsvFailure(t.message ?: "통신 오류")
                }
            })
    }


    // 이전 예약 정보 요청
    fun tryGetScheduleReserve(){

        val tripRetrofitInterface = ApplicationClass.sRetrofit.create(
            TripRetrofitInterface::class.java)

        tripRetrofitInterface.getScheduleRequest()
            .enqueue(object : Callback<GetScheduleResponse> {

                override fun onResponse(call: Call<GetScheduleResponse>,
                                        response: Response<GetScheduleResponse>) {
                    view.onGetScheduleSuccess(response.body() as GetScheduleResponse)
                }

                override fun onFailure(call: Call<GetScheduleResponse>, t: Throwable) {
                    view.onGetScheduleFailure(t.message ?: "통신 오류")
                }
            })
    }
}