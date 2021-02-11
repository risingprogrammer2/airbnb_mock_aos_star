package com.rp2.star.airbnb.src.log_in.input_profile

import android.util.Log
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.src.log_in.models.PostPhoneSignUpRequest
import com.rp2.star.airbnb.src.log_in.models.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputProfileService(val view: InputProfileActivityView) {

    // 회원가입 요청
    fun tryPostSignUp(postPhoneSignUpRequest: PostPhoneSignUpRequest){
        val inputProfileRetrofitInterface = ApplicationClass.sRetrofit.create(InputProfileRetrofitInterface::class.java)

        inputProfileRetrofitInterface.postSignUp(postPhoneSignUpRequest).enqueue(object:
            Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                Log.d("로그","postSignUp - Callback<SignUpResponse> - onResponse() called")
                Log.d("로그","call: $call , response: ${response.body()}")
                Log.d("로그","call: $call , SignUpResponse: ${response.body() as SignUpResponse}")
                view.onPostSignUpSuccess(response.body() as SignUpResponse)
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("로그","postSignUp - Callback<SignUpResponse> - onFailure() called")
                Log.d("로그","call: $call")
                view.onPostSignUpFailure(t.message ?: "통신 오류")
            }
        })
    }
}