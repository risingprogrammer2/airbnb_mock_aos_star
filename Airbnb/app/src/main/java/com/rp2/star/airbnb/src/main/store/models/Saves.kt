package com.rp2.star.airbnb.src.main.store.models

import com.google.gson.annotations.SerializedName

data class Saves (
    @SerializedName("folder") val folderName: String,
    @SerializedName("image") val imageList: ArrayList<String>
)