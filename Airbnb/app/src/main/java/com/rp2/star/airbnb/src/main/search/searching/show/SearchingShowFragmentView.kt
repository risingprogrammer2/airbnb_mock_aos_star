package com.rp2.star.airbnb.src.main.search.searching.show

import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeByCityResponse

interface SearchingShowFragmentView {

    // 도시명으로 숙소 정보 요청 콜백
    fun onGetLodgeSuccess(response: GetLodgeByCityResponse)

    fun onGetLodgeFailure(message: String)


}