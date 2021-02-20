package com.rp2.star.airbnb.src.main.search.searching.models

import com.google.gson.annotations.SerializedName

data class ResultLodgeDetail(
    @SerializedName("lodge") val lodgeUser: ResultLodgeDetailUser,
    @SerializedName("lodge_image") val lodgeImage: ArrayList<String>,
    @SerializedName("lodge_space") val lodgeSpace: ResultLodgeDetailLodgeSpace,
    /* "lodge_space": {
            "bedroom_cnt": 1,
            "bed_cnt": 2,
            "bathroom_cnt": 1.5
        },*/
    @SerializedName("lodge_space_detail") val lodgeSpaceDetail: ArrayList<ResultLodgeDetailLodgeSpaceDetail>,
    /*
     "lodge_space_detail": [
            {
                "id": 2,
                "category": "침실",
                "amount": 2,
                "name": "슈퍼싱글"
            }
     */
    @SerializedName("lodge_facility") val lodgeFacility: ArrayList<String>,
    /* "lodge_facility": [
            "에어컨",
            "무선 인터넷",
            "주방",
            "냉장고"
        ],*/
    @SerializedName("lodge_property") val lodgeProperty: ArrayList<String>,
    /*"lodge_property": [
            "집 전체",
            "청결강화",
            "셀프 체크인",
            "체크인 24시간 전까지 수수료 없이 취소 가능",
            "숙소 이용규칙"
        ],*/
    @SerializedName("lodge_review") var lodgeReview: ResultLodgeDetailLodgeReview? = null,
    @SerializedName("lodge_regulation") val Regulation: ArrayList<String>,
    @SerializedName("lodge_extra_regulation") val ExtraRegulation: String,
    /*
    "lodge_extra_regulation": "- 숙소내에서는 신발을 벗어주세요 신발장에 실내화가 준비되어 있어요\n- 사용하신 식기는 닦아주세요\n- 밤 9시이후에는 위층에 학생이 있습니다 조용히 해주세요"
    */
)