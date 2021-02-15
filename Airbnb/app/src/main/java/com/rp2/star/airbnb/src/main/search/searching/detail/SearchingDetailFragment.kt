package com.rp2.star.airbnb.src.main.search.searching.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentSearchingShowDetailBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView
import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeDetailResponse


class SearchingDetailFragment(val searchingView: SearchingActivityView) :
    BaseFragment<FragmentSearchingShowDetailBinding>(FragmentSearchingShowDetailBinding::bind,
    R.layout.fragment_searching_show_detail), SearchingDetailFragmentView {

    private val sp = ApplicationClass.sSharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val lodgeId = this.arguments!!.getInt("lodgeId", 26)
        //showLoadingDialog(this)
        // 넘겨받은 숙소 번호 조회, 없으면 26번 숙소 조회 하도록 임시 설정
        showLoadingDialog(context!!)
        SearchingDetailService(this as SearchingDetailFragmentView).tryGetLodgeDetail(lodgeId)


/*
        // 뒤로가기 버튼
        binding.searchingShowBack.setOnClickListener {
            searchingView.goToCalendar()
        }
*/
    }

    // 숙소 상세정보 조회 성공했을 때 실행
    override fun onGetLodgeDetailSuccess(response: GetLodgeDetailResponse) {
        dismissLoadingDialog()

        val result = response.result
        Log.d("로그", "onGetLodgeDetailSuccess() called, response: $response")
        Log.d("로그", "onGetLodgeDetailSuccess() called, message: $response.message")
        Log.d("로그", "onGetLodgeDetailSuccess() called, result: $result")
        Log.d("로그", "onGetLodgeDetailSuccess() called, lodgeReview: ${result.lodgeReview}")
        Log.d("로그", "onGetLodgeDetailSuccess() called, lodgeUser: ${result.lodgeUser}")
        Log.d("로그", "onGetLodgeDetailSuccess() called, lodgeSpace: ${result.lodgeSpace}")
        Log.d("로그", "onGetLodgeDetailSuccess() called, lodgeImage: ${result.lodgeImage}")
        Log.d("로그", "onGetLodgeDetailSuccess() called, lodgeFacility: ${result.lodgeFacility}")
        Log.d("로그", "onGetLodgeDetailSuccess() called, lodgeProperty: ${result.lodgeProperty}")

        showCustomToast("숙소 상세 조회 성공!")
    }

    // 숙소 상세정보 조회 통신이 아예 되지 않았을 때 실행
    override fun onGetLodgeDetailFailure(message: String) {
        dismissLoadingDialog()

        Log.d("로그", "onGetLodgeDetailFailure() called, message: $message")

        showCustomToast("네트워크 확인 후 다시 시도해주세요.\n message: $message")
    }
}