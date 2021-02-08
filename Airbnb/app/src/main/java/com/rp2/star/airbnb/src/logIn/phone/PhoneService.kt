package com.rp2.star.airbnb.src.logIn.phone

import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.logIn.models.PostCertNumCompRequest
import com.rp2.star.airbnb.src.logIn.models.PostCertNumRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhoneService(val view: PhoneActivityView) {

    // 인증번호 전송 요청
    fun tryPostCertNum(phoneNumber: String){
        // 핸드폰 번호
        val param = PostCertNumRequest(phoneNumber)

        val phoneRetrofitInterface = ApplicationClass.sRetrofit.create(PhoneRetrofitInterface::class.java)

        phoneRetrofitInterface.postCertNum(param).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostCertNumSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostCertNumFailure(t.message ?: "통신 오류")
            }
        })
    }

    // 인증번호 비교 요청
    fun tryPostCertNumCompare(postCertNumCompRequest: PostCertNumCompRequest){
        val phoneRetrofitInterface = ApplicationClass.sRetrofit.create(PhoneRetrofitInterface::class.java)

        phoneRetrofitInterface.postCertNumComp(postCertNumCompRequest).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onCompareCertNumSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostCertNumFailure(t.message ?: "통신 오류")
            }
        })
    }
}