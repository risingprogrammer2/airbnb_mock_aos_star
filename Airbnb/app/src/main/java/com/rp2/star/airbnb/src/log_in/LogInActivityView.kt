package com.rp2.star.airbnb.src.log_in

import com.kakao.sdk.auth.model.OAuthToken
import com.rp2.star.airbnb.src.log_in.models.SignUpResponse

interface LogInActivityView {

    // 카카오 서버에 카카오 로그인 요청 //
    fun onKakaoApiSuccess(token: OAuthToken)

    fun onKakaoApiFailure(message: String)

    // 팀 서버에 카카오 로그인 요청 //
    fun onPostKakaoLogInSuccess(response: SignUpResponse)

    fun onPostKakaoLogInFailure(message: String)

}