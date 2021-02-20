package com.rp2.star.airbnb.src.main.trip.models

import com.google.gson.annotations.SerializedName
import com.rp2.star.airbnb.config.BaseResponse

data class GetScheduleResponse (
    @SerializedName("result") val result: ArrayList<ScheduleResult>
):BaseResponse()