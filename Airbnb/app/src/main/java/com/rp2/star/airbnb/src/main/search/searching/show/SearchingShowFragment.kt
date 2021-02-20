package com.rp2.star.airbnb.src.main.search.searching.show

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.config.BaseResponse
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
        //검색 도시로 숙소 조회
        sp.getString("query", null)?.let{
           SearchingShowService(this).tryGetLodgeByCity(it)
        }

        // 서울로 숙소 조회
        // SearchingShowService(this).tryGetLodgeByCity("서울")
        //SearchingShowService(this).tryGetLodgeByCityDates("홍대")
        // 숙소 조회 리사이클러뷰 어댑터 설정
        lodgeByCityAdapter = SearchingShowLodgeAdapter(context!!, this, searchingView)
        binding.searchingShowRecyclerView.adapter = lodgeByCityAdapter
        binding.searchingShowRecyclerView.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)
        // 인디케이터 설정


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
                val lodgeCount = lodgeByCityList.size
                when(lodgeCount){
                    0 -> {
                        showCustomLongToast("해당 지역에는 하우스가 존재하지 않습니다.")
                        //searchingView.goToStep1()
                    }
                    else -> {
                        if(lodgeCount < 300){
                            binding.searchingShowLodgeNum.text = String.format("${lodgeCount}개의 숙소")
                        }else{
                            binding.searchingShowLodgeNum.text = String.format("300개 이상의 숙소")
                        }


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

    // 숙소 저장 통신 성공
    override fun onPostLodgeStoreSuccess(response: BaseResponse, pos: Int) {
        dismissLoadingDialog()

        Log.d("로그","onPostLodgeStoreSuccess() called - response: $response")

        when(response.isSuccess){
            true -> {
                // 성공 -> 하트 변경 , 저장 플래그 변경
                lodgeByCityList[pos].isSave = !(lodgeByCityList[pos].isSave)
                lodgeByCityAdapter.apply{
                    changeHeartState(pos)
                    notifyDataSetChanged()
                    //notifyItemChanged(pos)
                }

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
    override fun onDeleteLodgeStoreSuccess(response: BaseResponse, pos: Int) {
        dismissLoadingDialog()

        Log.d("로그", "onDeleteLodgeStoreSuccess() called - response: $response")

        when(response.isSuccess){
            true -> {
                lodgeByCityList[pos].isSave = !(lodgeByCityList[pos].isSave)
                lodgeByCityAdapter.apply{
                    changeHeartState(pos)
                    notifyDataSetChanged()
                    // notifyItemChanged(pos)
                }
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

    fun onHeartClicked(pos: Int, lodgeId: Int, isStored: Boolean){
        when(isStored){
            true -> SearchingShowService(this).tryDeleteLodgeStore(lodgeId, pos)
            false -> SearchingShowService(this)
                .tryPostStoreLodge("하드코딩 폴더", lodgeId, pos)
        }
    }

}