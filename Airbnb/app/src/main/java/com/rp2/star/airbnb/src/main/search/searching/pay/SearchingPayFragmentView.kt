package com.rp2.star.airbnb.src.main.search.searching.pay

import com.rp2.star.airbnb.config.BaseResponse

interface SearchingPayFragmentView {

    // 예약 요청 콜백
    fun onPostReserveSuccess(response: BaseResponse)

    fun onPostReserveFailure(message: String)

    fun changeGuests(total: Int)
}