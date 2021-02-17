package com.rp2.star.airbnb.src.main.search.searching.detail_calendar

import com.rp2.star.airbnb.src.main.search.searching.models.GetImpossibleDatesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchingDetailCalendarRetrofitInterface {

    // 예약 가능 날짜 확인
    @GET("/lodgings/{lodgingId}/calendars")
    fun getImpossibleDatesRequest(@Path("lodgingId") lodgeId: Int): Call<GetImpossibleDatesResponse>
}