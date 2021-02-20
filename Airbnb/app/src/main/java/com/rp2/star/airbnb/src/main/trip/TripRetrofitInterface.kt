package com.rp2.star.airbnb.src.main.trip

import com.rp2.star.airbnb.src.main.trip.models.GetScheduleResponse
import retrofit2.Call
import retrofit2.http.GET

interface TripRetrofitInterface {

    // 예정된 예약 정보 요청
    @GET("/users/schedules")
    fun getScheduleRequest(): Call<GetScheduleResponse>

    // 이전 예약 정보 요청
    @GET("/users/last-reservations")
    fun getPrevReserveRequest(): Call<GetScheduleResponse>


}