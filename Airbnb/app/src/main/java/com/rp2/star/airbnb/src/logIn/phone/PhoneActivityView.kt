package com.rp2.star.airbnb.src.logIn.phone

import com.rp2.star.airbnb.config.BaseResponse

interface PhoneActivityView {

    // 인증번호 전송 //
    fun onPostCertNumSuccess(response: BaseResponse)

    fun onPostCertNumFailure(message: String)

    // 인증번호 비교 //
    fun onCompareCertNumSuccess(response: BaseResponse)

    fun onCompareCertNumFailure(message: String)


}