package com.rp2.star.airbnb.src.main.trip

import com.rp2.star.airbnb.src.main.trip.models.GetScheduleResponse

interface TripFragmentView {

    // 숙소 상세 정보 요청 콜백
    fun onGetPrevRsvSuccess(response: GetScheduleResponse)

    fun onGetPrevRsvFailure(message: String)

    // 숙소 저장 요청 콜백
    fun onGetScheduleSuccess(response: GetScheduleResponse)

    fun onGetScheduleFailure(message: String)

}