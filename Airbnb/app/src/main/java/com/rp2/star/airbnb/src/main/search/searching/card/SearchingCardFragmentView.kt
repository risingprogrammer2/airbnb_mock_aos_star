package com.rp2.star.airbnb.src.main.search.searching.card

import com.rp2.star.airbnb.config.BaseResponse

interface SearchingCardFragmentView {

    // 예약 불가능 날짜 요청 콜백
    fun onPostCardInfoSuccess(response: BaseResponse)

    fun onPostCardInfoFailure(message: String)
}