package com.rp2.star.airbnb.src.main.search.searching.detail

import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeDetailResponse

interface SearchingDetailFragmentView {

    // 숙소 상세 정보 요청 콜백
    fun onGetLodgeDetailSuccess(response: GetLodgeDetailResponse)

    fun onGetLodgeDetailFailure(message: String)

    // 숙소 저장 요청 콜백
    fun onPostLodgeStoreSuccess(response: BaseResponse)

    fun onPostLodgeStoreFailure(message: String)

    // 숙소 저장 취소 콜백
    fun onDeleteLodgeStoreSuccess(response: BaseResponse)

    fun onDeleteLodgeStoreFailure(message: String)

}