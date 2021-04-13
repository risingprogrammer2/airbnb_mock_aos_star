package com.rp2.star.airbnb.src.main.store.on_store

interface OnStoreView {

    // 숙소 저장 요청 API 콜
    fun storeLodge(folderName: String)
}