package com.rp2.star.airbnb.src.log_in.phone

import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.log_in.models.SignUpResponse

interface PhoneActivityView {

    // 인증번호 전송 //
    fun onPostCertNumSuccess(response: BaseResponse)

    fun onPostCertNumFailure(message: String)

    // 인증번호 비교 //
    fun onCompareCertNumSuccess(response: SignUpResponse)

    fun onCompareCertNumFailure(message: String)


}