package com.rp2.star.airbnb.src.main.search.searching.detail_calendar

import com.rp2.star.airbnb.src.main.search.searching.models.GetImpossibleDatesResponse

interface SearchingDetailCalendarFragmentView {

    // 예약 불가능 날짜 요청 콜백
    fun onGetImpossibleDatesSuccess(response: GetImpossibleDatesResponse)

    fun onGetImpossibleDatesFailure(message: String)
}