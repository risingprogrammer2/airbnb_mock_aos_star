package com.rp2.star.airbnb.src.main.search.searching.card

import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.main.search.searching.models.PostCardInfoBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchingCardService(val view: SearchingCardFragmentView) {

    val accessToken = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

    // 카드 등록 요청
    fun tryPostCardInfo(userId: Int, creditNumber: String, creditPeroid: String,
                        creditCVV: String, postalCode: String?){
        val postCardInfoBody = PostCardInfoBody(userId, creditNumber, creditPeroid,
            creditCVV, postalCode)

        val searchingCardRetrofitInterface = ApplicationClass.sRetrofit.create(
            SearchingCardRetrofitInterface::class.java)

        searchingCardRetrofitInterface.postCardInfoRequest(postCardInfoBody)
            .enqueue(object : Callback<BaseResponse> {

                override fun onResponse(call: Call<BaseResponse>,
                                        response: Response<BaseResponse>) {
                    view.onPostCardInfoSuccess(response.body() as BaseResponse)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onPostCardInfoFailure(t.message ?: "통신 오류")
                }
            })
    }
}