package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class ResultLodgeDetail(
    @SerializedName("lodgeUser") val lodgeUser: ResultLodgeDetailUser,
    @SerializedName("lodgeImage") val lodgeImage: ArrayList<ResultLodgeDetailLodgeImges>,
    @SerializedName("lodgeSpace") val lodgeSpace: ArrayList<ResultLodgeDetailLodgeFacility>,
    @SerializedName("lodgeFacility") val lodgeFacility: ArrayList<ResultLodgeDetailLodgeFacility>,
    @SerializedName("lodgeProperty") val lodgeProperty: ArrayList<ResultLodgeDetailLodgeProperty>,
    @SerializedName("lodgeReview") val lodgeReview: ArrayList<ResultLodgeDetailLodgeReview>,
)