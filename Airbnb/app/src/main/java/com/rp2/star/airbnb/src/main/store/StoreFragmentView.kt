package com.rp2.star.airbnb.src.main.store

import com.rp2.star.airbnb.src.main.store.models.GetFoldersResponse

interface StoreFragmentView {

    // 도시명으로 숙소 정보 요청 콜백
    fun onGetFoldersSuccess(response: GetFoldersResponse)

    fun onGetFoldersFailure(message: String)
}