package com.rp2.star.airbnb.src.main.search.searching.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewData(
    val imgUrl: String?,
    val name: String,
    val dates: String,
    val contents: String,
): Parcelable