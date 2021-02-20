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

    // show Fragment로 이동 - 검색 결과
    fun goToShow()

    // detail Fragment로 이동 - 숙소 상세 정보 화면
    fun goToDetail(lodgeId: Int)

    // review Fragment로 이동 - 후기 자세히 보기
    fun goToReview()

    // detail calendar Fragment로 이동 - 예약 가능 날짜 확인
    fun goToDetailCalendar(lodgeId: Int)

    // pay Fragment로 이동 - 결제 화면
    fun goToPay(lodgeId: Int)

    // card Fragment로 이동 - 카드 등록 모달
    fun goToCard()

    // Company2 Fragment로 이동 (일행)
    fun goToCompany2()
}