package com.rp2.star.airbnb.src.main.search.searching

interface SearchingActivityView {

    // step1 Fragment로 이동
    fun goToStep1()

    // step2 Fragment로 이동
    fun goToStep2()

    // calendar Fragment로 이동
    fun goToCalendar()

    // Company Fragment로 이동 (일행)
    fun goToCompany()

    // show Fragment로 이동
    fun goToShow()
}