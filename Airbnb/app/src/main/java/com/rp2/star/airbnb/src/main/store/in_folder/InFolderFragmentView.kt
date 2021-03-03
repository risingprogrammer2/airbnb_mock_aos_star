package com.rp2.star.airbnb.src.main.store.in_folder

import com.rp2.star.airbnb.src.main.store.models.GetLodgesInFolderResponse

interface InFolderFragmentView {

    // 도시명으로 숙소 정보 요청 콜백
    fun onGetFoldersSuccess(response: GetLodgesInFolderResponse)

    fun onGetFoldersFailure(message: String)
}