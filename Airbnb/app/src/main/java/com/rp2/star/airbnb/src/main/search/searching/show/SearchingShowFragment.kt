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
import com.rp2.star.airbnb.src.main.store.on_store.OnStoreBtmFragment


class SearchingShowFragment(private val searchingView: SearchingActivityView) :
    BaseFragment<FragmentSearchingShowBinding>(FragmentSearchingShowBinding::bind,
    R.layout.fragment_searching_show), SearchingShowFragmentView{

    private val sp = ApplicationClass.sSharedPreferences
    private var lodgeByCityList = ArrayList<ResultLodgeByCity>()
    private lateinit var lodgeByCityAdapter: SearchingShowLodgeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 서버 이용을 못해서 임시 주석 처리

        showLoadingDialog(context!!)
        //검색 도시로 숙소 조회
        sp.getString("query", null)?.let{
           SearchingShowService(this).tryGetLodgeByCity(it)
        }

        // 서울로 숙소 조회
        // SearchingShowService(this).tryGetLodgeByCity("서울")
        // 숙소 조회 리사이클러뷰 어댑터 설정
        lodgeByCityAdapter = SearchingShowLodgeAdapter(context!!, this, searchingView)
        binding.searchingShowRecyclerView.adapter = lodgeByCityAdapter
        binding.searchingShowRecyclerView.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)

        /*
        // 서버 사용 못해서 더미데이터 사용
        val temp_city = ResultLodgeByCity(0, "Y", "신라호텔", "뉴욕",
            "아파트", "전체", 2, 3.5.toFloat(), 1.5.toFloat(),
            4.6.toFloat(), 5, false, arrayListOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F8dPeO%2FbtqA8j5fFTx%2FMaxPXI78hOQcCgdG2SHTw0%2Fimg.jpg"),
            ResultLodgeByCitySpaces(1.toFloat(), 1.toFloat(), 1.toFloat()), 300000, 900000)
        lodgeByCityAdapter.provideList(arrayListOf(temp_city))*/

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
    override fun onPostLodgeStoreSuccess(response: BaseResponse, pos: Int, folderName: String) {
        dismissLoadingDialog()

        Log.d("로그","onPostLodgeStoreSuccess() called - response: $response")

        when(response.isSuccess){
            true -> {
                // 성공 -> 하트 변경 , 저장 플래그 변경, 성공 토스트 메시지
                lodgeByCityList[pos].isSave = !(lodgeByCityList[pos].isSave)
                lodgeByCityAdapter.apply{
                    changeHeartState(pos)
                    notifyDataSetChanged()
                    //notifyItemChanged(pos)
                }
                showCustomLongToast("${folderName}에 저장됨")

                // 바텀시트, 그 위에 있는 다이얼로그 모두 내린다
                val btmFragment =
                    childFragmentManager.findFragmentByTag("onStore") as OnStoreBtmFragment
                btmFragment.dismiss()
                // btmFragment.createFolderDialog.dismiss()
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
    override fun onDeleteLodgeStoreSuccess(response: BaseResponse, pos: Int, folderName: String) {
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
                showCustomLongToast("${folderName}에서 삭제됨")
            }
            false -> {
                Log.d("로그", "숙소 저장 취소 실패 - message: ${response.message}")
                showCustomLongToast("저장 숙소 삭제에 실패했습니다, 잠시 후 다시 시도해주세요.\n" +
                        "원인: ${response.message}"
                )
            }
        }


    }

    // 숙소 저장 취소통신 실패
    override fun onDeleteLodgeStoreFailure(message: String) {
        Log.d("로그","onDeleteLodgeStoreFailure() called - message: $message")

        showCustomToast("숙소 저장 취소 통신 실패: message: $message")
    }
}