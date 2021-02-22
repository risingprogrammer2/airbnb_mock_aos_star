package com.rp2.star.airbnb.src.main.store.in_folder.models

import com.google.gson.annotations.SerializedName
import com.rp2.star.airbnb.config.BaseResponse

data class Folder (
    @SerializedName("folder") val folderName: String,
    @SerializedName("image") val imageList: ArrayList<String>
):BaseResponse()