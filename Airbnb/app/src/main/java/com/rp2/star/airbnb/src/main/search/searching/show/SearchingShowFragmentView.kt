package com.rp2.star.airbnb.src.main.search.searching.show

import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeByCityResponse

interface SearchingShowFragmentView {

    // 도시명으로 숙소 정보 요청 콜백
    fun onGetLodgeSuccess(response: GetLodgeByCityResponse)

    fun onGetLodgeFailure(message: String)

    // 숙소 저장 요청 콜백
    fun onPostLodgeStoreSuccess(response: BaseResponse, pos: Int, folderName: String)

    fun onPostLodgeStoreFailure(message: String)

    // 숙소 저장 취소 콜백
    fun onDeleteLodgeStoreSuccess(response: BaseResponse, pos: Int, folderName: String)

    fun onDeleteLodgeStoreFailure(message: String)
}