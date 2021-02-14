package com.rp2.star.airbnb.src.main.search.searching

interface SearchingActivityView {

    // step2 Fragment로 이동
    fun goToStep2(query: String)

    // calendar Fragment로 이동
    fun goToCalendar()

    // Company Fragment로 이동 (일행)
    fun goToCompany()
}