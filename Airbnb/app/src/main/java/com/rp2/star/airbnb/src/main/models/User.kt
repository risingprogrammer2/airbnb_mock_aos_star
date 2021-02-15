package com.rp2.star.airbnb.src.main.models

import com.google.gson.annotations.SerializedName

data class User(
    // ProfileResponse에서 사용
    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("create_date") var createDate: String? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("residence") var residence: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("work") var work: String? = null,
    @SerializedName("verify") var verify: Boolean = false,
    @SerializedName("email_verify") var emailVerify: Boolean = false,
    @SerializedName("phone_verify") var phoneVerify: Boolean = false,
    @SerializedName("img") var img: String? = null
)