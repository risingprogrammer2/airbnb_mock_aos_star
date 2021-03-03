package com.rp2.star.airbnb.src.main.store.models

import com.google.gson.annotations.SerializedName

data class InFolder (
    @SerializedName("saves") val folder: ArrayList<InFolderSaves>
)