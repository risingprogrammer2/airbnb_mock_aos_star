package com.rp2.star.airbnb.src.main.search.searching.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.databinding.FragmentSearchingShowDetailBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView
import com.rp2.star.airbnb.src.main.search.searching.models.GetImpossibleDatesResponse
import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeDetailResponse


class SearchingDetailFragment(val searchingView: SearchingActivityView) :
    BaseFragment<FragmentSearchingShowDetailBinding>(FragmentSearchingShowDetailBinding::bind,
    R.layout.fragment_searching_show_detail), SearchingDetailFragmentView {

    private val sp = ApplicationClass.sSharedPreferences
    private var isStored: Boolean = false
    private var lodgeId = 26
    private var dayCnt = 0
    private var price = "0"
    private var location0: String = ""
    private var title: String = ""
    private var space: String = ""
    private var rating: String = ""
    private var dates: String = ""
    private var imgUrl: String = ""
    private lateinit var pagerAdapter: DetailImageSliderAdapter
    private lateinit var lodgeImgUrlList: ArrayList<String>
    // 후기 자세히 보기 -> 평점 String, 평가 항목들 ArrayList<Float>, 후기들 ArrayList<LodgeReview> 전달
    private lateinit var mBundle: Bundle
    private lateinit var ratingList: FloatArray


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailHostImg.clipToOutline = true

        dayCnt = sp.getInt("dayCnt", 0)
        price = sp.getString("price", "0").toString()


        lodgeImgUrlList = ArrayList()
        // 해당 숙소를 저장했는지?
        isStored = sp.getBoolean("isStored", false)
        // 전달받은 해당 숙소 아이디
        lodgeId = this.arguments!!.getInt("lodgeId", 26)
        sp.edit().putInt("lodgeId", lodgeId).apply()

        // 넘겨받은 숙소 번호 조회, 없으면 26번 숙소 조회 하도록 임시 설정
        showLoadingDialog(context!!)
        SearchingDetailService(this as SearchingDetailFragmentView).tryGetLodgeDetail(lodgeId)

        // 찜하기
        binding.detailStore.setOnClickListener(onStoreListener)
        // 화면 안의 뒤로가기 버튼
        binding.detailBack.setOnClickListener {
            searchingView.goToShow()
        }

        // 후기 모두 보기 버튼
        binding.detailReviewBtn.setOnClickListener(onClickReview)

        // 예약 가능 날짜 확인 버튼
        binding.detailBtnCalendar.setOnClickListener(onClickCalendar)

        binding.detailBtnPay.setOnClickListener(onClickPay)
    }

    // 찜하기 버튼 클릭 리스너
    private val onStoreListener = View.OnClickListener{
        when(isStored){
            false -> {
                showLoadingDialog(context!!)
                SearchingDetailService(this).tryPostStoreLodge("하드코딩 폴더", lodgeId)
            }
            true -> {
                showLoadingDialog(context!!)
                SearchingDetailService(this).tryDeleteLodgeStore(lodgeId)
            }

        }
    }

    private val onClickReview = View.OnClickListener {
        searchingView.goToReview()
    }

    private val onClickCalendar = View.OnClickListener {
        sp.edit().putInt("company2", 0).commit()
        searchingView.goToDetailCalendar(lodgeId)
    }

    private val onClickPay = View.OnClickListener {

        searchingView.goToPay(lodgeId)
    }

    // 숙소 상세정보 조회 성공했을 때 실행 - 받아온 정보들 화면에 적용
    override fun onGetLodgeDetailSuccess(response: GetLodgeDetailResponse) {
        Log.d("로그", "onGetLodgeDetailSuccess() called, response: $response")

        val result = response.result
        val lodgeImage = result.lodgeImage
        val lodgeUser = result.lodgeUser
        val lodgeProperty = result.lodgeProperty
        val lodgeSpace = result.lodgeSpace
        val lodgeReview = result.lodgeReview


        Log.d("로그", "onGetLodgeDetailSuccess() called, message: $response.message")
        Log.d("로그", "onGetLodgeDetailSuccess() called, result: $result")
        Log.d("로그", "onGetLodgeDetailSuccess() called, lodgeReview: $lodgeReview")
        Log.d("로그", "onGetLodgeDetailSuccess() called, lodgeUser: ${result.lodgeUser}")
        Log.d("로그", "onGetLodgeDetailSuccess() called, lodgeSpace: $lodgeSpace")
        Log.d("로그", "onGetLodgeDetailSuccess() called, lodgeImage: $lodgeImage")
        Log.d("로그", "onGetLodgeDetailSuccess() called, lodgeFacility: ${result.lodgeFacility}")
        Log.d("로그", "onGetLodgeDetailSuccess() called, lodgeProperty: ${result.lodgeProperty}")

        // 숙소 이미지가 들어있는 (인덱스,이미지) 리스트에서
        // 이미지만 가져와서 로컬 리스트에 저장한다.
        for (i in 0 until lodgeImage.size) {
            lodgeImgUrlList.add(lodgeImage[i])
        }
        imgUrl = lodgeImage[0]
        sp.edit().putString("imgUrl", imgUrl).commit()

        // 뷰페이저 어댑터를 생성하고, 뷰페이저와 연결한다.
        pagerAdapter = DetailImageSliderAdapter(this, lodgeImgUrlList)
        binding.detailViewPager.adapter = pagerAdapter

        // 찜 유뮤 반영
        if (isStored) {
            binding.detailStore.setImageResource(R.drawable.searching_detail_heart_filled)
        }

        // 첫번째 섹션
        // 숙소 이름
        binding.detailLodgeName.text = lodgeUser.name
        // 숙소의 대략적 위치
        val location: String = String.format(
            "${lodgeUser.city}, ${lodgeUser.area}," +
                    " ${lodgeUser.capital}"
        )
        binding.detailLocation.text = location
        // 슈퍼호스트 이면 "슈퍼호스트" 표시 활성화
        when (lodgeUser.superhost) {
            "Y" -> {
                binding.detailSuperhostImg.visibility = View.VISIBLE
                binding.detailSuperhostText.visibility = View.VISIBLE
            }
            else -> {
            }
        }

        // 평점이 있으면 평점 (개수) or null
        var rating1: String? = null
        var rating2: String? = null // 아래에 큰 글씨로 적혀있는 후기
        var startIdx = 0    // (
        var endIdx = 0      // )
        when (lodgeUser.count) {
            // 후기,평가가 아직 없으면
            0 -> {
                // 별 그림 삭제
                binding.detailRatingRatingStar.visibility = View.GONE
                rating2 = String.format("후기 (아직) 없음")
                // 환불정책 어쩌구 저쩌구
                binding.detailReviewComments.text =
                    resources.getString(R.string.searching_dtl_txt_no_review)
                // 후기 자세히보는 버튼 삭제
                binding.detailReviewBtn.visibility = View.GONE

            }
            // 후기가 있으면
            else -> {
                rating1 = String.format("${lodgeUser.rating} (${lodgeUser.count})")
                rating2 = String.format("${lodgeUser.rating}점 (후기 ${lodgeUser.count})개")
                startIdx = rating1.indexOf("(")
                endIdx = rating1.lastIndex
                // 괄호부분만 회색으로 변경
                val ssb = SpannableStringBuilder(rating1)
                ssb.setSpan(
                    resources.getColor(R.color.greyForMainBtmText), startIdx, endIdx,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.detailRatingText.text = rating1
            }
        }
        binding.detailRatingRatingText.text = rating2


        // 두번째 섹션: 숙소, 장소 타입 반영
        val lodgeType = lodgeUser.lodgingType
        val placeType = lodgeUser.placeType
        binding.detailPlaceType.text = String.format("$lodgeType $placeType")

        // 최대 인원, 침실, 침대, 욕실 개수 반영
        var tempStr: String? = null
        tempStr = String.format("${lodgeUser.totalUser}")
        val bedRoomStr = String.format(" • 침실 ${lodgeSpace.bedroomCnt}개 ")
        val bedStr = String.format(" • 침대 ${lodgeSpace.bedCnt}개 ")
        val bathRoomStr = String.format(" • 욕실 ${lodgeSpace.bathroomCnt}개 ")
        binding.detailTotalAndSpaces.text = String.format("$tempStr$bedRoomStr$bedStr$bathRoomStr")


        // 호스트 프로필 이미지
        val imgUrl = lodgeUser.hostImg
        Glide.with(context!!)
            .load(imgUrl)
            .error(R.drawable.my_page_me)
            .placeholder(R.drawable.my_page_me)
            .into(binding.detailHostImg)

        // 숙소 특성(집 전체, 이용 규칙 등) 적용
        for (i in 0 until lodgeProperty.size) {
            // 이미지 적용
            when (lodgeProperty[i]) {
                "집 전체" ->
                    binding.detailProperty0Layout.visibility = View.VISIBLE
                "청결강화" -> binding.detailProperty1Layout.visibility = View.VISIBLE
                "셀프 체크인" -> binding.detailProperty2Layout.visibility = View.VISIBLE
                "체크인 24시간 전까지 수수료 없이 취소 가능" -> binding.detailProperty3Layout.visibility =
                    View.VISIBLE
                "숙소 이용규칙" -> binding.detailProperty4Layout.visibility = View.VISIBLE
            }

            // 숙소 추가 정보
            val moreInfo: String? = lodgeUser.lodgeInfo
            if(moreInfo != null && moreInfo != "") {
                binding.detailTxtInfo.text = moreInfo
                if(binding.detailTxtInfo.lineCount >= 6) {
                    binding.detailBtnInfo.visibility = View.VISIBLE
                }
                else{
                    binding.detailBtnInfo.visibility = View.GONE
                }
            }
            else{
                binding.detailTxtInfo.visibility = View.GONE
                binding.detailBtnInfo.visibility = View.GONE
            }
        }

            // 리뷰 목록이 있으면 첫번째 후기를 보여준다
            // 리뷰 작성자
            // 프로필 사진
            if (lodgeReview != null) {
                Glide.with(context!!)
                    .load(lodgeReview.img)
                    .error(R.drawable.my_page_me)
                    .into(binding.detailReviewImg)

                // 이름
                binding.detailReviewName.text = lodgeReview.firstName
                // 날짜 -> yyyy년 mm월으로 파싱
                val dates =
                    String.format(
                        "${
                            lodgeReview
                                .createDate
                                .substring(0..6)
                                .replace("-", "년 ")
                        }월"
                    )
                //var datenow = LocalDate.parse(lodgeReviewList[0].createDate, DateTimeFormatter.ISO_DATE)
                binding.detailReviewDates.text = dates
                // 코멘트
                binding.detailReviewComments.text = lodgeReview.comment


                // 아래는 수정돼서 삭제
                /* // 평가항목들 FloatArray로 만들어서 Bundle로 전달
            lodgeUser.apply{
                ratingList = floatArrayOf(communicationRating, checkInRating, accuracyRating, positionRating,
                satisfactionRating, cleanRating)
                mBundle.putFloatArray("ratingList", ratingList)
            }
            // 리뷰객체 리스트 전달
            mBundle.putParcelableArrayList("reviewList", lodgeReviewList)*/
            }

            // 날짜가 선택되어 있는지?
            if(dayCnt > 0){
                val kwrTxt = resources.getString(R.string.kwr)
                binding.detailBtmPrice.text = String.format("${kwrTxt}${price} / 1박")
                binding.detailBtnCalendar.visibility = View.GONE
                binding.detailBtnPay.visibility = View.VISIBLE
            }else{
                binding.detailBtmPrice.text = String.format("요금을\n확인하려면")
                        binding.detailBtnCalendar.visibility = View.VISIBLE
                        binding.detailBtnPay.visibility = View.GONE
            }

            dismissLoadingDialog()

            //showCustomToast("숙소 상세 조회 성공!")
    }

    // 숙소 상세정보 조회 통신이 아예 되지 않았을 때 실행
    override fun onGetLodgeDetailFailure(message: String) {
        dismissLoadingDialog()

        Log.d("로그", "onGetLodgeDetailFailure() called, message: $message")

        showCustomToast("네트워크 확인 후 다시 시도해주세요.\n message: $message")
        searchingView.goToCompany()
    }

    // 숙소 저장 통신 성공
    override fun onPostLodgeStoreSuccess(response: BaseResponse) {
        Handler(Looper.getMainLooper()).postDelayed({
            dismissLoadingDialog()
        }, 800)

        Log.d("로그","onPostLodgeStoreSuccess() called - response: $response")

        when(response.isSuccess){
            true -> {
                // 성공 -> 하트 변경 , 저장 플래그 변경
                binding.detailStore.setImageResource(R.drawable.searching_detail_heart_filled)
                isStored = true
            }
            false -> {
                showCustomToast("저장에 실패했습니다, 잠시 후 다시 시도해주세요.\n" +
                        "원인: ${response.message}")
            }
        }
    }

    // 숙소 저장 통신 실패
    override fun onPostLodgeStoreFailure(message: String) {
        dismissLoadingDialog()

        Log.d("로그", "onGetLodgeDetailFailure() called, message: $message")

        showCustomToast("저장 시도 실패: 네트워크 확인 후 다시 시도해주세요.\n message: $message")
    }

    // 숙소 저장 취소 통신 성공
    override fun onDeleteLodgeStoreSuccess(response: BaseResponse) {
        Handler(Looper.getMainLooper()).postDelayed({
            dismissLoadingDialog()
        }, 800)

        Log.d("로그", "onDeleteLodgeStoreSuccess() called - response: $response")

        when(response.isSuccess){
            true -> {
                binding.detailStore.setImageResource(R.drawable.searching_detail_heart_blank)
                isStored = false
            }
            false -> {
                Log.d("로그", "숙소 저장 취소 실패 - message: ${response.message}")
                showCustomLongToast("저장 숙소 삭제에 실패했습니다, 잠시 후 다시 시도해주세요.\n" +
                        "원인: ${response.message}")
            }
        }


    }

    // 숙소 저장 취소통신 실패
    override fun onDeleteLodgeStoreFailure(message: String) {
        Log.d("로그","onDeleteLodgeStoreFailure() called - message: $message")

        showCustomToast("숙소 저장 취소 통신 실패: message: $message")
    }

    override fun onGetImpossibleDatesSuccess(response: GetImpossibleDatesResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetImpossibleDatesFailure(message: String) {
        TODO("Not yet implemented")
    }

}