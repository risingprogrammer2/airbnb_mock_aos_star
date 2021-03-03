package com.rp2.star.airbnb.src.main.store.models

import com.google.gson.annotations.SerializedName
import com.rp2.star.airbnb.config.BaseResponse

data class GetLodgesInFolderResponse (
    @SerializedName("result") val result: InFolderSaves
): BaseResponse()