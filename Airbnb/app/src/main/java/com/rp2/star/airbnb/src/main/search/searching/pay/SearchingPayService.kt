package com.rp2.star.airbnb.src.main.search.searching.pay

import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.main.search.searching.models.PostReserveBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchingPayService(val view: SearchingPayFragmentView) {

    val accessToken = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

    // 카드 등록 요청
    fun tryPostReserveLodge(startDate: String, endDate: String, headCount: String,
                            message: String, paymentId: Int, lodgingId: Int){

        val postReserveBody = PostReserveBody(startDate, endDate, headCount,
            message, paymentId, couponName = 1, lodgingId)

        val searchingPayRetrofitInterface = ApplicationClass.sRetrofit.create(
            SearchingPayRetrofitInterface::class.java)

        searchingPayRetrofitInterface.postReserveRequest(postReserveBody)
            .enqueue(object : Callback<BaseResponse> {

                override fun onResponse(call: Call<BaseResponse>,
                                        response: Response<BaseResponse>) {
                    view.onPostReserveSuccess(response.body() as BaseResponse)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onPostReserveFailure(t.message ?: "통신 오류")
                }
            })
    }
}