package com.rp2.star.airbnb.src.main.search.searching.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultLodgeDetailLodgeReview(
    @SerializedName("comment") val comment: String,
    @SerializedName("profile_img") val img: String?,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("create_date") val createDate: String,
): Parcelable