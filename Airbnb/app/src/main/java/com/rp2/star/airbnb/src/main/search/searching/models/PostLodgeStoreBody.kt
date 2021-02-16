package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class PostLodgeStoreBody (
    @SerializedName("listName") val listName: String,
    @SerializedName("lodgeId") val lodgeId: Int
)
