package com.rp2.star.airbnb.src.main.trip

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentMainTripBinding
import com.rp2.star.airbnb.src.main.trip.models.GetScheduleResponse


class TripFragment : BaseFragment<FragmentMainTripBinding>(FragmentMainTripBinding::bind,
    R.layout.fragment_main_trip), TripFragmentView{

    private lateinit var mContext: Context
    private lateinit var prevFragment: TripPrevFragment
    private lateinit var scheduledFragment: TripScheduledFragment
    private var isPrevLoaded: Boolean = false
    private var isScheduledLoaded: Boolean = false
    private lateinit var pagerAdapter: TripPagerAdapter
    private var fragmentList = ArrayList<Fragment>(2)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(context!!)

        Log.d("로그", "로그1")
        // 모든 예약 정보를 가져온다
        TripService(this).tryGetPrevReserve()
        TripService(this).tryGetScheduleReserve()

        val tripRetrofitInterface1 = ApplicationClass.sRetrofit.create(
            TripRetrofitInterface::class.java)
        val tripRetrofitInterface2 = ApplicationClass.sRetrofit.create(
            TripRetrofitInterface::class.java)


        val mRunnable = Runnable {
            tripRetrofitInterface1.getPrevReserveRequest().execute().apply{
                when(isSuccessful){
                    true ->{
                        Log.d("로그","onGetPrevRsvSuccess() called - response: ${body()}")

                        when(body()!!.isSuccess) {
                            true -> {
                                val result = body()!!.result
                                prevFragment = TripPrevFragment(result)
                            }

                            false -> {
                                //dismissLoadingDialog()
                                showCustomLongToast(
                                    "지난 예약 조회에 실패했습니다, 메세지를 확인해주세요." +
                                            "\nmessage: ${body()!!.message}"
                                )
                            }
                        }
                    }
                    false -> {
                        Log.d("로그","onGetPrevRsvFailure() called - 여행 네트워크 통신 실패")
                        //dismissLoadingDialog()

                        showCustomToast("이전 예약 조회 통신 실패: message: 여행 네트워크 통신 실패")
                    }
                }
                isPrevLoaded = true
            }
            tripRetrofitInterface2.getScheduleRequest().execute().apply{
                when(isSuccessful){
                    true ->{
                        Log.d("로그","getScheduleRequestSuccess() called - response: ${body()}")

                        when(body()!!.isSuccess) {
                            true -> {
                                val result = body()!!.result
                                scheduledFragment = TripScheduledFragment(result)
                                //dismissLoadingDialog()
                            }

                            false -> {
                                dismissLoadingDialog()
                                showCustomLongToast(
                                    "예정된 예약 조회에 실패했습니다, 메세지를 확인해주세요." +
                                            "\nmessage: ${body()!!.message}"
                                )
                            }
                        }
                    }
                    false -> {
                        Log.d("로그","getScheduleRequestFailure() called - 여행 네트워크 통신 실패")
                        //dismissLoadingDialog()

                        showCustomToast("예정 예약 조회 통신 실패: message: 여행 네트워크 통신 실패")
                    }
                }
                isScheduledLoaded = true
            }
        }
        val mRunnable2 = Runnable {
            while(!isPrevLoaded || !isScheduledLoaded) {}

            activity!!.runOnUiThread{
                // 예약/예정 프래그먼트를 매개변수로 전달해서 페이저 어댑터 생성
                pagerAdapter = TripPagerAdapter(this, prevFragment, scheduledFragment)

                binding.tripViewPager.adapter = pagerAdapter

                TabLayoutMediator(binding.tripTabLayout, binding.tripViewPager){tab, position ->
                    val stringId = resources.getIdentifier("trip_txt_tab${position}",
                        "string", activity!!.packageName)
                    tab.text = getString(stringId)
                }.attach()
                dismissLoadingDialog()
            }
        }
        val mThread = Thread(mRunnable)
        val mThread2 = Thread(mRunnable2)
        mThread.start()
        mThread2.start()
    }

    override fun onGetPrevRsvSuccess(response: GetScheduleResponse) {
        Log.d("로그","onGetPrevRsvSuccess() called - response: $response")
        isPrevLoaded = true

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
        isPrevLoaded = true
        dismissLoadingDialog()

        showCustomToast("이전 예약 조회 통신 실패: message: $message")
    }

    override fun onGetScheduleSuccess(response: GetScheduleResponse) {
        Log.d("로그","onGetScheduleSuccess() called - response: $response")

        isScheduledLoaded = true

        when(response.isSuccess){
            true -> {
                scheduledFragment = TripScheduledFragment(response.result)
            }

            false -> {
                showCustomLongToast("예정된 예약 조회에 실패했습니다, 메세지를 확인해주세요." +
                        "\nmessage: ${response.message}")
            }
        }
    }

    override fun onGetScheduleFailure(message: String) {
        Log.d("로그","onGetScheduleFailure() called - message: $message")
        isScheduledLoaded = true

        showCustomToast("예정된 예약 조회 통신 실패: message: $message")
    }
}