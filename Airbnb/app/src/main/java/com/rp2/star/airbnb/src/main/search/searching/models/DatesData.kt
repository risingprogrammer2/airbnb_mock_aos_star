package com.rp2.star.airbnb.src.main.search.searching.models

import java.io.Serializable

data class DatesData(
    var startDate: String,
    var startMonth: String,
    var startYear: String?,
    var EndDate: String? = null,
    var EndMonth: String? = null,
    var EndYear: String? = null,
) : Serializable