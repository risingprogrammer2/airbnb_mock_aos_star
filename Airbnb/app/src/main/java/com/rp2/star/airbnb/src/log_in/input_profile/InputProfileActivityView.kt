package com.rp2.star.airbnb.src.log_in.input_profile

import com.rp2.star.airbnb.src.log_in.models.SignUpResponse

interface InputProfileActivityView {

    // 프로필 전달 -> 회원가입 요청 //
    fun onPostSignUpSuccess(response: SignUpResponse)

    fun onPostSignUpFailure(message: String)
}