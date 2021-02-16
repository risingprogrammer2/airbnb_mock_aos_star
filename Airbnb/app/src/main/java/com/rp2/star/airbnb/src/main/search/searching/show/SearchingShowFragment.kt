package com.rp2.star.airbnb.src.main.search.searching.show

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentSearchingShowBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView
import com.rp2.star.airbnb.src.main.search.searching.models.GetLodgeByCityResponse
import com.rp2.star.airbnb.src.main.search.searching.models.ResultLodgeByCity


class SearchingShowFragment(private val searchingView: SearchingActivityView) :
    BaseFragment<FragmentSearchingShowBinding>(FragmentSearchingShowBinding::bind,
    R.layout.fragment_searching_show), SearchingShowFragmentView{

    private val sp = ApplicationClass.sSharedPreferences
    private var lodgeByCityList = ArrayList<ResultLodgeByCity>()
    lateinit var lodgeByCityAdapter: SearchingShowLodgeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        showLoadingDialog(context!!)
        // 검색 도시로 숙소 조회
//        sp.getString("query", null)?.let{
//            SearchingShowService(this).tryGetLodgeByCity(it)
//        }
        // 서울로 숙소 조회
        SearchingShowService(this).tryGetLodgeByCity("서울")
        // 숙소 조회 리사이클러뷰 어댑터 설정
        lodgeByCityAdapter = SearchingShowLodgeAdapter(context!!, searchingView)
        binding.searchingShowRecyclerView.adapter = lodgeByCityAdapter
        binding.searchingShowRecyclerView.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)

/*
        // 뒤로가기 버튼
        binding.searchingShowBack.setOnClickListener {
            searchingView.goToCalendar()
        }
*/
    }

    // 도시명으로 숙소정보 조회 성공했을 때 실행
    override fun onGetLodgeSuccess(response: GetLodgeByCityResponse) {
        dismissLoadingDialog()
        Log.d("로그", "onGetLodgeSuccess() called, response: $response")

        val result = response.result
        when(response.isSuccess){
            true -> {
                Log.d("로그", "onGetLodgeSuccess() called, result.lodge: $lodgeByCityList")
                lodgeByCityList = result.lodge
                when(lodgeByCityList.size){
                    0 -> showCustomToast("해당 지역에는 하우스가 존재하지 않습니다.")
                    else -> {
                        lodgeByCityAdapter.provideList(lodgeByCityList)
                    }
                }

            }
            false -> {
                showCustomLongToast("검색 오류: ${response.message}")
                searchingView.goToStep1()
            }
        }


        // showCustomToast("도시명 숙소 조회 성공!")
    }

    // 도시명으로 숙소정보 조회 통신이 아예 되지 않았을 때 실행
    override fun onGetLodgeFailure(message: String) {
        dismissLoadingDialog()

        Log.d("로그", "onGetLodgeFailure() called, message: $message")

        showCustomToast("네트워크 확인 후 다시 시도해주세요.\n message: $message")
    }
}