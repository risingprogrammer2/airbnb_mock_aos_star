package com.rp2.star.airbnb.src.log_in

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.AccessTokenInfo
import com.kakao.sdk.user.model.User
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.src.log_in.models.NaverResponse
import com.rp2.star.airbnb.src.log_in.models.PostKakaoLogInRequest
import com.rp2.star.airbnb.src.log_in.models.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInService(val view: LogInActivityView) {


    // 팀 서버에 네이버 로그인/회원가입 통신 요청
    fun tryNaverLogIn(accessToken: String){

        val logInRetrofitInterface = ApplicationClass.sRetrofit.create(LogInRetrofitInterface::class.java)

        logInRetrofitInterface.postNaverLogInRequest(accessToken)
            .enqueue(object : Callback<NaverResponse> {

                override fun onResponse(call: Call<NaverResponse>, response: Response<NaverResponse>) {
                    view.onPostNaverLogInSuccess(response.body() as NaverResponse)
                }

                override fun onFailure(call: Call<NaverResponse>, t: Throwable) {
                    view.onPostNaverLogInFailure(t.message ?: "통신 오류")
                }
            })
    }


    // 팀 서버에 카카오 로그인/회원가입 통신 요청
    fun tryKakaoLogIn(accessToken: String){

        val postKakaoLogInRequest = PostKakaoLogInRequest(accessToken)
        val logInRetrofitInterface = ApplicationClass.sRetrofit.create(LogInRetrofitInterface::class.java)

        logInRetrofitInterface.postKakaoLogInRequest(postKakaoLogInRequest)
            .enqueue(object : Callback<SignUpResponse> {

            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                view.onPostKakaoLogInSuccess(response.body() as SignUpResponse)
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                view.onPostKakaoLogInFailure(t.message ?: "통신 오류")
            }
        })
    }

    // 카카오 서버에 카카오 로그인 요청
    fun kakaoLogIn(context: Context){
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (LoginClient.instance.isKakaoTalkLoginAvailable(context)) {
            Log.d("로그", "카카오톡 앱으로 로그인 called")
            LoginClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            Log.d("로그", "카카오톡 계정으로 로그인 called")
            LoginClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    // 카카오 서버에 로그인 요청에 대한 콜백
    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        // 카카오 서버 API 로그인 실패
        if (error != null){
            view.onKakaoApiFailure(error.message!!)
        }
        // 카카오 서버 API 로그인 성공
        else if(token != null){
            view.onKakaoApiSuccess(token)
        }
    }

    //카카오 사용자 프로필 객체 반환
    fun kakaoGetProfile(): User? {
        var returnValue: User? = null

        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("로그", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i("로그", "사용자 정보 요청 성공, user: $user")
                Log.i("로그","properties: ${user.properties}")
                Log.i("로그","properties: ${user.properties}")
                val nickname: String? = user.properties?.get("nickname")
                val img: String? = user.properties?.get("thumbnail_image")
                ApplicationClass.sSharedPreferences.edit().apply{
                    putString("firstName", nickname)
                    putString("img", img)
                    apply()
                }
            }
        }
        return returnValue
    }

    // 카카오 액세스 토큰 객체 반환 -> id 따로 빼야 함
    fun kakaoGetTokenInfo(): AccessTokenInfo? {
        var returnValue: AccessTokenInfo? = null

        // 토큰 정보 보기
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e("태그", "토큰 정보 보기 실패", error)
            }
            else if (tokenInfo != null) {
                Log.i("태그", "토큰 정보 보기 성공, tokenInfo: $tokenInfo")
                returnValue = tokenInfo
            }
        }
        return returnValue
    }

}