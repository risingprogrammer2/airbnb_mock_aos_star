package com.rp2.star.airbnb.src.main.trip

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentMainTripBinding
import com.rp2.star.airbnb.src.main.trip.models.GetScheduleResponse


class TripFragment : BaseFragment<FragmentMainTripBinding>(FragmentMainTripBinding::bind,
    R.layout.fragment_main_trip), TripFragmentView{

    private lateinit var mContext: Context
    private lateinit var prevFragment: TripPrevFragment
    private lateinit var scheduledFragment: TripScheduledFragment
    private var isLoading: Boolean = true
    private lateinit var pagerAdapter: TripPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = context!!

        Log.d("로그", "로그1")
        // 모든 예약 정보를 가져온다
        showLoadingDialog(context!!)
        TripService(this).tryGetPrevReserve()
        Thread.sleep(1000)
        TripService(this).tryGetScheduleReserve()
        if(isLoading){
            dismissLoadingDialog()
            isLoading = false
        }
        Log.d("로그", "로그2")
        // 예약/예정 프래그먼트를 매개변수로 전달해서 페이저 어댑터 생성


    }

    override fun onGetPrevRsvSuccess(response: GetScheduleResponse) {
        Log.d("로그","onGetPrevRsvSuccess() called - response: $response")

        when(response.isSuccess){
            true -> {
                prevFragment = TripPrevFragment(response.result)

            }

            false -> {
                showCustomLongToast("지난 예약 조회에 실패했습니다, 메세지를 확인해주세요." +
                        "\nmessage: ${response.message}")
            }
        }
    }

    override fun onGetPrevRsvFailure(message: String) {
        Log.d("로그","onGetPrevRsvFailure() called - message: $message")
        dismissLoadingDialog()
        isLoading = false

        showCustomToast("이전 예약 조회 통신 실패: message: $message")
    }

    override fun onGetScheduleSuccess(response: GetScheduleResponse) {
        Log.d("로그","onGetScheduleSuccess() called - response: $response")

        when(response.isSuccess){
            true -> {
                scheduledFragment = TripScheduledFragment(response.result)

                pagerAdapter = TripPagerAdapter(this)
                pagerAdapter.provideFragment1(prevFragment)
                pagerAdapter.provideFragment2(scheduledFragment)


                binding.tripViewPager.adapter = pagerAdapter

                TabLayoutMediator(binding.tripTabLayout, binding.tripViewPager){tab, position ->
                    val stringId = resources.getIdentifier("trip_txt_tab${position}",
                        "string", activity!!.packageName)
                    tab.text = getString(stringId)
                }.attach()

            }

            false -> {
                showCustomLongToast("예정된 예약 조회에 실패했습니다, 메세지를 확인해주세요." +
                        "\nmessage: ${response.message}")
            }
        }
    }

    override fun onGetScheduleFailure(message: String) {
        Log.d("로그","onGetScheduleFailure() called - message: $message")
        if(isLoading) {
            dismissLoadingDialog()
            isLoading = false
        }

        showCustomToast("예정된 예약 조회 통신 실패: message: $message")
    }
}