package com.rp2.star.airbnb.src.main.store.in_folder.models

import com.google.gson.annotations.SerializedName
import com.rp2.star.airbnb.config.BaseResponse

data class GetFoldersResponse (
    @SerializedName("result") val result: ArrayList<Folder>
): BaseResponse()